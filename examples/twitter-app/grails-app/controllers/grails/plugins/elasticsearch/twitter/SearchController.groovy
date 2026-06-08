package grails.plugins.elasticsearch.twitter

import grails.plugins.elasticsearch.ElasticSearchResult

class SearchController {
    def elasticSearchService

    def searchEntities() {
        // perform global search
        def searchHits = params.query ? elasticSearchService.search("${params.query}", [score: true]) : new ElasticSearchResult()
        render(view: 'searchResults', model: [hits: searchHits])
    }
}
