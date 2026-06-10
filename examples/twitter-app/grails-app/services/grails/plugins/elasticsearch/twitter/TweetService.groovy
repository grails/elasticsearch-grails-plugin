package grails.plugins.elasticsearch.twitter

import grails.gorm.transactions.Transactional

@Transactional
class TweetService {

    def addTweet(Tweet tweet) {
        tweet.save(flush: true, failOnError: true)
    }
}
