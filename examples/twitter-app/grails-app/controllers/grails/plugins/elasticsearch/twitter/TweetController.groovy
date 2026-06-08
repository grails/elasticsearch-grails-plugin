package grails.plugins.elasticsearch.twitter

class TweetController {
    def elasticSearchService

    def index() {
        redirect action: 'list'
    }

    def list() {
        return [tweets: Tweet.findAll()]
    }

    def search() {
        if (!params.query) {
            redirect action: 'list'
        }
        // perform global search
        def searchHits = elasticSearchService.search("${params.query}", [score: true])
        render(view: '/search/searchResults', model: [tweets: []])
    }
}
