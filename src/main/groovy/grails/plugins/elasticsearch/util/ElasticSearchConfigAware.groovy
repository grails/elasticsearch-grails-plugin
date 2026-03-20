package grails.plugins.elasticsearch.util

import grails.core.GrailsApplication
import groovy.transform.CompileStatic

/**
 * Created by marcoscarceles on 02/06/2016.
 */
@CompileStatic
trait ElasticSearchConfigAware {

    abstract GrailsApplication getGrailsApplication()

    ConfigObject getIndexSettings() {
        (esConfig?.index as ConfigObject)?.settings as ConfigObject
    }

    ConfigObject getEsConfig() {
        grailsApplication?.config?.elasticSearch as ConfigObject
    }

    ConfigObject getMigrationConfig() {
        (grailsApplication?.config?.elasticSearch as ConfigObject)?.migration as ConfigObject
    }
}
