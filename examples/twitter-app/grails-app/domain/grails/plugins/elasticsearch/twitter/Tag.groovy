package grails.plugins.elasticsearch.twitter

class Tag {
    static searchable = {
        except = ['boostValue']
    }

    static mapping = {
        id generator: 'sequence'
    }

    String name
    Integer boostValue = 1
}
