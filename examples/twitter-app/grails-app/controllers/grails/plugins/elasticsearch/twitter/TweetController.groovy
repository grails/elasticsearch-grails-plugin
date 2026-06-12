package grails.plugins.elasticsearch.twitter

class TweetController {
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
        // perform domain class search, effectively only looks in message
        def searchHits = Tweet.search("${params.query}", [score: true])
        render(view: 'list', model: [tweets: searchHits.searchResults])
    }
}
