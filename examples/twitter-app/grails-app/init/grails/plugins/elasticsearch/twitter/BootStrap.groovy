package grails.plugins.elasticsearch.twitter

class BootStrap {

    def init = {
        User.withTransaction {
            new User(lastname: 'Smith', firstname: 'Alexander').save(failOnError: true)
            new User(lastname: 'Doe', firstname: 'John').save(failOnError: true)
            new User(lastname: 'Doe', firstname: 'Jane').save(failOnError: true)
        }
        Tag.withTransaction {
            new Tag(name: 'location').save(failOnError: true)
            new Tag(name: 'question').save(failOnError: true)
            new Tag(name: 'information').save(failOnError: true)
            new Tag(name: 'answer').save(failOnError: true)
        }
        def alex = User.findByFirstname('Alexander')
        def john = User.findByFirstname('John')
        def jane = User.findByFirstname('Jane')
        Tweet.withTransaction {
            new Tweet(user: alex, message: 'Alexander entered the room')
                    .addToTags(Tag.findByName('location'))
                    .save(failOnError: true)
            new Tweet(user: alex, message: 'Alexander left the room')
                    .addToTags(Tag.findByName('location'))
                    .save(failOnError: true)
            new Tweet(user: john, message: 'John entered the building')
                    .addToTags(Tag.findByName('location'))
                    .save(failOnError: true)
            new Tweet(user: jane, message: 'Where is everyone?')
                    .addToTags(Tag.findByName('question'))
                    .save(failOnError: true)
            new Tweet(user: jane, message: 'This example is fun and entertaining.')
                    .addToTags(Tag.findByName('information'))
                    .save(failOnError: true)
            new Tweet(user: jane, message: 'Add some tweets and enjoy!')
                    .addToTags(Tag.findByName('information'))
                    .save(failOnError: true)
        }
    }

    def destroy = {
    }

}