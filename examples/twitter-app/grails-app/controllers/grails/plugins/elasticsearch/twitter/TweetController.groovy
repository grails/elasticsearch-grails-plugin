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
        // perform domain class search
        def searchHits = Tweet.search("${params.query}", [score: true])
        // FIXME: we have a mapping problem with users and tags, so that they are not rehydrated correctly.
        //  We thus load full tweets from database to obtain user and tags, too
        def tweets = searchHits.searchResults.collect { Tweet.load(it.id) }
        render(view: 'list', model: [tweets: tweets])
    }
}
