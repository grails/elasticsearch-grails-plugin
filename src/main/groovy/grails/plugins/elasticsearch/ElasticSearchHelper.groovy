package grails.plugins.elasticsearch

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.opensearch.client.RestHighLevelClient

class ElasticSearchHelper {

    RestHighLevelClient elasticSearchClient

    def <R> R withElasticSearch(@ClosureParams(value=SimpleType, options="org.opensearch.client.RestHighLevelClient") Closure<R> callable) {
        callable.call(elasticSearchClient)
    }

}
