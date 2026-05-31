package grails.plugins.elasticsearch.twitter

class BootStrap {

    def init = {
        User.withTransaction {
            new User(lastname: 'Smith', firstname: 'Alexander').save(failOnError: true)
            new User(lastname: 'Doe', firstname: 'John').save(failOnError: true)
            new User(lastname: 'Doe', firstname: 'Jane').save(failOnError: true)
        }
        def alex = User.findByFirstname('Alexander')
        def john = User.findByFirstname('John')
        def jane = User.findByFirstname('Jane')
        Tweet.withTransaction {
            new Tweet(user: alex, message: 'Alexander entered the room').save(failOnError: true)
            new Tweet(user: alex, message: 'Alexander left the room').save(failOnError: true)
            new Tweet(user: john, message: 'John entered the building').save(failOnError: true)
            new Tweet(user: jane, message: 'Where is everyone?').save(failOnError: true)
            new Tweet(user: jane, message: 'This example is fun and entertaining.').save(failOnError: true)
            new Tweet(user: jane, message: 'Add some tweets and enjoy!').save(failOnError: true)
        }
    }

    def destroy = {
    }

}