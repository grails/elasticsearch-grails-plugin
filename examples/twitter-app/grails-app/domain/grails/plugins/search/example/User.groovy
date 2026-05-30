package grails.plugins.search.example

class User {
    static mapping = {
        table 'users'
    }

    static searchable = {
        only = ['lastname', 'firstname', 'activity']
        lastname boost: 20
        firstname boost: 15, index: 'true'
        someThings index: 'false'
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
    String password
    String activity = 'Evildoer'
    String someThings = 'something'
    ArrayList<String> listOfThings = ['this', 'that', 'and this']
}
