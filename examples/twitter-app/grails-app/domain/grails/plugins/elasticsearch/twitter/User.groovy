package grails.plugins.elasticsearch.twitter

class User {
    static mapping = {
        table 'users'
        firstname unique: true
        password nullable: true, blank: true
        id generator: 'sequence'
    }

    static searchable = {
        only = ['lastname', 'firstname']
        lastname boost: 20
        firstname boost: 15, index: 'true' // This leads to a keyword field in index mapping supporting exact matches only
        tweets component: true
    }

    static constraints = {
        tweets cascade: 'all'
    }
    static hasMany = [
        tweets: Tweet
    ]
    static mappedBy = [
        tweets: 'user'
    ]

    String lastname
    String firstname
    String password = ''

    @Override
    String toString() {
        return "$lastname, $firstname"
    }
}
