package grails.plugins.elasticsearch.twitter

class TweetController {
    def elasticSearchService
    def tweetService

    def index() {
        redirect action: 'list'
    }

    def add() {
        [ users: User.list(), tags: Tag.list()]
    }

    def post() {
        def tweet = new Tweet()
        bindData(tweet, params)
        tweetService.addTweet(tweet)
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
