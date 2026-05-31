package grails.plugins.elasticsearch.twitter

class Tag {
    static searchable = {
        except = ['boostValue']
    }

    String name
    Integer boostValue = 1
}
