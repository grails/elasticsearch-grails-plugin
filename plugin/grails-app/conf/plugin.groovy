elasticSearch {
    /**
     * Date formats used by the unmarshaller of the JSON responses
     */
    date.formats = ["yyyy-MM-dd'T'HH:mm:ss.S'Z'"]

    /**
     * Hosts for remote ElasticSearch instances.
     * Will only be used with the "transport" client mode.
     * If the client mode is set to "transport" and no hosts are defined, ["localhost", 9200] will be used by default.
     */
    client.hosts = [
            [host: 'localhost', port: 9200]
    ]

    /**
     * Whether to enable SSL for the ElasticSearch client.
     */
    client.ssl.enabled = false

    /**
     * The strategy for validating SSL connections.
     *
     * Possible values:
     *   - "all": trust all certificates; least secure, only use for development
     *   - "self-signed": trust self-signed certificates; use in trusted environments like a properly secured docker-stack
     *   - "trust-store": use a given trust store; requires trust-store file and credentials
     */
    client.ssl.trust = all

    /**
     * If <code>client.ssl.trust</code> is set to "trust-store", you need to provide the path to the trust store file here.
     */
    client.ssl.truststore.file = ''

    /**
     * If <code>client.ssl.trust</code> is set to "trust-store", you need to provide the credentials for the trust store file here.
     */
    client.ssl.truststore.password = ''

    client

    /**
     * Default mapping property exclusions
     *
     * No properties matching the given names will be mapped by default
     * ie, when using "searchable = true"
     *
     * This does not apply for classes using mapping by closure
     */
    defaultExcludedProperties = ['password']

    /**
     * Determines if the plugin should reflect any database save/update/delete automatically
     * on the ES instance. Default to false.
     */
    disableAutoIndex = false

    /**
     * Should the database be indexed at startup.
     *
     * The value may be a boolean true|false.
     * Indexing is always asynchronous (compared to Searchable plugin) and executed after BootStrap.groovy.
     */
    bulkIndexOnStartup = true

    /**
     *  Max number of requests to process at once. Reduce this value if you have memory issue when indexing a big amount of data
     *  at once. If this setting is not specified, 500 will be use by default.
     */
    maxBulkRequest = 500

    /**
     * Should component-mapped properties be unmarshalled. The default is true.
     */
    unmarshallComponents = true

    /**
     * The name of the ElasticSearch mapping configuration property that annotates domain classes. The default is 'searchable'.
     */
    searchableProperty.name = 'searchable'

    /**
     * The strategy to be used in case of a conflict installing mappings
     */
    migration.strategy = 'alias'

    /**
     * Whether to replace existing indices with aliases when there's a conflict and the 'alias' strategy is chosen
     */
    migration.aliasReplacesIndex = true

    /**
     * When set to false, in case of an alias migration, prevents the alias to point to the newly created index
     */
    migration.disableAliasChange = false

    index.settings.numberOfReplicas = 0

    /**
     * Whether to index and search all non excluded transient properties. All explicitly included transients in @only@ will be indexed regardless.
     */
    includeTransients = false

    /**
     * Disable dynamic method injection in domain class
     */
    disableDynamicMethodsInjection = false

    /**
     * Search method name in domain class, defaults to search
     */
    searchMethodName = "search"

    /**
     * countHits method name in domain class, defaults to search
     */
    countHitsMethodName = "countHits"

    plugin.mapperAttachment.enabled = true
}

environments {
    development {
        /**
         * Possible values: "transport"
         * If set to null, "transport" mode is used by default.
         */
        elasticSearch.client.mode = 'transport'
    }
    test {
        elasticSearch.client.mode = 'transport'
    }
    production {
        elasticSearch.client.mode = 'transport'
    }
}