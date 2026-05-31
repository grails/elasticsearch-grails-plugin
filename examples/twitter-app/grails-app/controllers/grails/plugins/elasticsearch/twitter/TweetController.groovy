package grails.plugins.elasticsearch.twitter

class TweetController {

    def index() { }

    def list() {
        def allTweets = Tweet.findAll()
        return [tweets: allTweets]
    }
}
