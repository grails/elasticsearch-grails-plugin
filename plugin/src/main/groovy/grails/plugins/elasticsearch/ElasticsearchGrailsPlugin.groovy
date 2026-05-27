package grails.plugins.elasticsearch

import grails.plugins.*
import grails.plugins.elasticsearch.conversion.CustomEditorRegistrar
import grails.plugins.elasticsearch.conversion.JSONDomainFactory
import grails.plugins.elasticsearch.conversion.unmarshall.DomainClassUnmarshaller
import grails.plugins.elasticsearch.index.IndexRequestQueue
import grails.plugins.elasticsearch.mapping.DomainReflectionService
import grails.plugins.elasticsearch.mapping.MappingMigrationManager
import grails.plugins.elasticsearch.mapping.SearchableClassMappingConfigurator
import grails.plugins.elasticsearch.unwrap.DomainClassUnWrapperChain
import grails.plugins.elasticsearch.unwrap.HibernateProxyUnWrapper
import grails.plugins.elasticsearch.util.DomainDynamicMethodsUtils

class ElasticsearchGrailsPlugin extends Plugin {

       def grailsVersion = "7.0.0  > *"

       def loadAfter = ['services', 'mongodb']

       // resources that are excluded from plugin packaging
       def pluginExcludes = [
           "grails-app/views/error.gsp",
           "**/test/**",
           "src/docs/**"
       ]

       def title = "ElasticSearch Grails Plugin"
       def author = 'Puneet Behl'
       def authorEmail = 'puneet.behl007@gmail.com'
       def description = '''The revived Elasticsearch plugin for Grails 7.'''
       def profiles = ['web']
       def documentation = "https://grails-plugins.github.io/grails-elasticsearch/latest/"

       def license = "APACHE"

       // Details of company behind the plugin (if there is one)
       def organization = [ name: 'TO THE NEW', url: 'https://www.tothenew.com']

       // Any additional developers beyond the author specified above.
       def developers = [
               [name: 'Noam Y. Tenne', email: 'noam@10ne.org'],
               [name: 'Marcos Carceles', email: 'marcos.carceles@gmail.com'],
               [name: 'Puneet Behl', email: 'puneet.behl007@gmail.com'],
               [name: 'James Kleeh', email: 'james.kleeh@gmail.com'],
               [name: 'Mihael Koep', email: 'mihael.koep@softwareschneiderei.de']
       ]

       def issueManagement = [ system: "GitHub", url: "https://github.com/grails-plugins/grails-elasticsearch/issues" ]

       def scm = [ url: "https://github.com/grails-plugins/grails-elasticsearch" ]

       Closure doWithSpring() {
           { ->
               ConfigObject esConfig = config.elasticSearch

               domainReflectionService(DomainReflectionService) { bean ->
                   mappingContext = ref('grailsDomainClassMappingContext')

                   grailsApplication = grailsApplication
               }

               elasticSearchContextHolder(ElasticSearchContextHolder) {
                   config = esConfig
                   proxyHandler = ref('proxyHandler')
               }
               elasticSearchHelper(ElasticSearchHelper) {
                   elasticSearchClient = ref('elasticSearchClient')
               }
               elasticSearchClient(ClientNodeFactoryBean) { bean ->
                   elasticSearchContextHolder = ref('elasticSearchContextHolder')
                   bean.destroyMethod = 'shutdown'
               }
               indexRequestQueue(IndexRequestQueue) {
                   elasticSearchContextHolder = ref('elasticSearchContextHolder')
                   elasticSearchClient = ref('elasticSearchClient')
                   jsonDomainFactory = ref('jsonDomainFactory')
                   domainClassUnWrapperChain = ref('domainClassUnWrapperChain')
               }
               mappingMigrationManager(MappingMigrationManager) {
                   elasticSearchContextHolder = ref('elasticSearchContextHolder')
                   grailsApplication = grailsApplication
                   es = ref('elasticSearchAdminService')
               }
               searchableClassMappingConfigurator(SearchableClassMappingConfigurator) { bean ->
                   elasticSearchContext = ref('elasticSearchContextHolder')
                   grailsApplication = grailsApplication
                   es = ref('elasticSearchAdminService')
                   mmm = ref('mappingMigrationManager')
                   domainReflectionService = ref('domainReflectionService')
               }
               domainInstancesRebuilder(DomainClassUnmarshaller) {
                   elasticSearchContextHolder = ref('elasticSearchContextHolder')
                   elasticSearchClient = ref('elasticSearchClient')
                   grailsApplication = grailsApplication
               }
               customEditorRegistrar(CustomEditorRegistrar) {
                   grailsApplication = grailsApplication
               }

               if (manager?.hasGrailsPlugin('hibernate') || manager?.hasGrailsPlugin('hibernate4')) {
                   hibernateProxyUnWrapper(HibernateProxyUnWrapper)
               }

               domainClassUnWrapperChain(DomainClassUnWrapperChain)

               jsonDomainFactory(JSONDomainFactory) {
                   elasticSearchContextHolder = ref('elasticSearchContextHolder')
                   grailsApplication = grailsApplication
                   domainClassUnWrapperChain = ref('domainClassUnWrapperChain')
                   domainReflectionService = ref('domainReflectionService')
               }

               elasticSearchBootStrapHelper(ElasticSearchBootStrapHelper) {
                   grailsApplication = grailsApplication
                   elasticSearchService = ref('elasticSearchService')
                   elasticSearchContextHolder = ref('elasticSearchContextHolder')
                   elasticSearchAdminService = ref('elasticSearchAdminService')
               }

               if (!esConfig.disableAutoIndex) {
                   if (!esConfig.datastoreImpl) {
                       throw new Exception('No datastore implementation specified')
                   }
                   auditListener(AuditEventListener, ref(esConfig.datastoreImpl)) {
                       elasticSearchContextHolder = ref('elasticSearchContextHolder')
                       indexRequestQueue = ref('indexRequestQueue')
                   }
               }
           }
       }

       void doWithApplicationContext() {
           def configurator = applicationContext.getBean(SearchableClassMappingConfigurator)
           configurator.configureAndInstallMappings()

           if (!grailsApplication.config.getProperty("elasticSearch.disableDynamicMethodsInjection", Boolean, false)) {
               DomainDynamicMethodsUtils.injectDynamicMethods(grailsApplication, applicationContext)
           }
       }
}
