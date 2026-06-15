package grails.plugins.elasticsearch.twitter

class Tweet {
    static searchable = {
        message boost: 2.0
    }

    static mapping = {
        id generator: 'sequence'
    }

    static belongsTo = [
        user: User
    ]

    static hasMany = [
        tags: Tag
    ]

    static constraints = {
        tags nullable: true
    }

    String message = ''
    Date dateCreated = new Date()


    @Override
    String toString() {
        return "$dateCreated: $message"
    }
}
