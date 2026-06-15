/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grails.plugins.elasticsearch

import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.config.RequestConfig
import org.apache.http.conn.ssl.TrustAllStrategy
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.ssl.SSLContextBuilder
import org.elasticsearch.client.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.FactoryBean

import javax.net.ssl.SSLContext

class ClientNodeFactoryBean implements FactoryBean {

    private static final Logger LOG = LoggerFactory.getLogger(this)

    ElasticSearchContextHolder elasticSearchContextHolder
    RestHighLevelClient restHighLevelClient

    Object getObject() {

        int connectTimeout = 2
        int socketTimeout = 30

        String connectionScheme = elasticSearchContextHolder.config.client.ssl.enabled ? 'https' : null
        RestClientBuilder builder = RestClient.builder(new HttpHost('localhost', 9200, connectionScheme))

        // Configure transport addresses
        if (elasticSearchContextHolder.config.client.hosts) {
            List<HttpHost> httpHostList = []
            elasticSearchContextHolder.config.client.hosts.each {
                int port = (it.port instanceof String)? Integer.valueOf(it.port) : it.port
                httpHostList << new HttpHost("${it.host}", port, connectionScheme)
            }
            HttpHost[] httpHosts = httpHostList
            builder = RestClient.builder(httpHosts)
        }

        // Configure username and password credentials
        if (elasticSearchContextHolder.config.client.username) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider()
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticSearchContextHolder.config.client.username, elasticSearchContextHolder.config.client.password))
            builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    if (elasticSearchContextHolder.config.client.ssl.enabled) {
                        configureSSL(httpClientBuilder)
                    }
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                }
            })
        }

        LOG.debug 'Initializing Elasticsearch RestClient'
        //builder.setMaxRetryTimeoutMillis(timeout * 1000)
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                if (elasticSearchContextHolder.config.client.connectTimeout) {
                    connectTimeout = elasticSearchContextHolder.config.client.connectTimeout as int
                    LOG.debug "Set REST client connect timeout to ${connectTimeout} seconds"
                }
                if (elasticSearchContextHolder.config.client.socketTimeout) {
                    socketTimeout = elasticSearchContextHolder.config.client.socketTimeout as int
                    LOG.debug "Set REST client socket timeout to ${socketTimeout} seconds"
                }
                return requestConfigBuilder.setConnectTimeout(connectTimeout * 1000).setSocketTimeout(socketTimeout * 1000)
                        .setConnectionRequestTimeout(0)
            }
        })
        def highLevelClientBuilder = new RestHighLevelClientBuilder(builder.build())
        highLevelClientBuilder.setApiCompatibilityMode(true)
        restHighLevelClient = highLevelClientBuilder.build()
        LOG.debug 'Initialized Elasticsearch RestClient'

        return restHighLevelClient
    }

    // TODO: check further ssl configuration options
    private void configureSSL(HttpAsyncClientBuilder httpClientBuilder) {
        SSLContextBuilder sslContextBuilder = SSLContextBuilder.create()
        if (elasticSearchContextHolder.config.client.ssl.trust == 'all') {
            sslContextBuilder.loadTrustMaterial(new TrustAllStrategy())
        }
        if (elasticSearchContextHolder.config.client.ssl.trust == 'self-signed') {
            sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy())
        }
        if (elasticSearchContextHolder.config.client.ssl.trust == 'trust-store') {
            def trustStoreFile = elasticSearchContextHolder.config.client.ssl.truststore.file as File
            def trustStorePassword = elasticSearchContextHolder.config.client.ssl.truststore.password as String
            if (!trustStoreFile || !trustStoreFile.canRead() || ! trustStorePassword) {
                throw new IllegalArgumentException("If you set elasticsearch.client.ssl.trust to 'trust-store' you must provide a truststore file and a truststore password!")
            }
            sslContextBuilder.loadTrustMaterial(trustStoreFile, trustStorePassword.toCharArray())
        }
        httpClientBuilder.setSSLContext(sslContextBuilder.build())
    }

    @Override
    Class getObjectType() {
        return Client
    }

    @Override
    boolean isSingleton() {
        return true
    }

    def shutdown() {
        LOG.info 'Closing RestClient'
        restHighLevelClient.close()
    }
}
