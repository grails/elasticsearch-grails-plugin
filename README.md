[![Maven Central](https://img.shields.io/maven-central/v/org.grails.plugins/grails-elasticsearch)](https://central.sonatype.com/artifact/org.grails.plugins/grails-elasticsearch)
[![License](https://img.shields.io/github/license/grails-plugins/grails-elasticsearch)](https://www.apache.org/licenses/LICENSE-2.0)
[![CI](https://github.com/grails-plugins/grails-elasticsearch/actions/workflows/ci.yml/badge.svg)](https://github.com/grails-plugins/grails-elasticsearch/actions/workflows/ci.yml)

# Elasticsearch Grails plugin

[Documentation about the plugin](https://grails-plugins.github.io/grails-elasticsearch/)

## How can I obtain the plugin?

Just like any other Grails plugin, through the Grails Plugin center.
Edit your project's `build.gradle` file, by adding the plugin's dependency declaration:

### Grails 7+

build.gradle:
```groovy
dependencies {
    ...
    implementation "org.grails.plugins:grails-elasticsearch:5.0.0"
    ...
}
```

### Grails 5/6

build.gradle:
```groovy
dependencies {
    ...
    implementation "org.grails.plugins:elasticsearch:4.0.0"
    ...
}
```

## Versioning

In order to simply the versioning, with v3.0.0.M1 the Elasticsearch Grails plugin is using Semantic Versioning 2.0.0.
To understand what that means, please see the [specification documentation](https://semver.org/).

The plugin version is neither tied to the Grails release nor the Elasticsearch client API used. Often it is possible to
connect to newer Elasticsearch servers if you do not require the new features.

| Plugin Version | Grails Version | Elasticsearch Client API Version |
|----------------|----------------|----------------------------------|
| 5.0.0          | 7.x            | 7.17.29                          |
| 4.0.0          | 5.3.x - 6.x.x  | 7.17.29                          |
| 3.0.0          | 4.0.x - 6.x.x  | 7.8.0                            |
| 2.7.0          | 3.3.x          | 7.3.0                            |
| 2.5.0          | 3.3.x          | 5.5.3                            |
| 2.4.2          | 3.3.y          | 5.4.3                            |
| 2.4.1          | 3.3.x          | 5.4.3                            |
| 2.4.0          | 3.3.y          | 5.4.3                            |
| 1.4.1          | 3.1.y          | 5.4.1                            |
| 1.2.1          | 3.2.y          | 2.3.z                            |
| 1.2.0          | 3.1.y          | 2.3.z                            |
| 1.0.0.x        | 3.1.y          | 1.6.z                            |
| 0.1.0.x        | 2.y            | 2.1.z                            |
| 0.0.4.x        | 2.y            | 1.6.z                            |
           

## Example Configuration for Grails 7

In the following we demonstrate two example configurations using Hibernate or MongoDB as a datastore.

### Hibernate

Check out the example application in this repository on using Elasticsearch in Grails 7.x with Hibernate.

Add the following to your `grails-app/conf/application.yml`:
```yml
elasticSearch:
    plugin:
        mapperAttachment:
            enabled: false
    client:
        mode: transport
        hosts:
        - {host: localhost, port: 9200}
    cluster.name: elasticsearch
    bulkIndexOnStartup: true
    datastoreImpl: hibernateDatastore
```

### MongoDB

Add the MongoDB dependency to `build.gradle`:
```groovy
dependencies {
  compile 'org.grails.plugins:mongodb:7.0.0'
}
```

And the configuration to `grails-app/conf/application.yml`:
```yml
elasticSearch:
    plugin:
        mapperAttachment:
            enabled: false
    client:
        mode: transport
        hosts:
        - {host: localhost, port: 9200}
    cluster.name: elasticsearch
    bulkIndexOnStartup: true
    datastoreImpl: mongoDatastore
```

### NOTE

This project is a fork based on the great work done by the guys at:

* https://github.com/mstein/elasticsearch-grails-plugin
* https://github.com/noamt/elasticsearch-grails-plugin

### Why would you want to fork the original Elasticsearch plugin?

The original Elasticsearch plugin relies on *Hibernate-specific* GORM components, thus rendering it unusable if you back your Grails application by any other database.

### Here, have a yak:
```
                            _,,,_
                        .-'`  (  '.
                     .-'    ,_  ;  \___      _,
                 __.'    )   \'.__.'(:;'.__.'/
         __..--""       (     '.__{':');}__.'
       .'         (    ;    (   .-|` '  |-.
      /    (       )     )      '-p     q-'
     (    ;     ;          ;    ; |.---.|
     ) (              (      ;    \ o  o)
     |  )     ;       |    )    ) /'.__/
     )    ;  )    ;   | ;       //
     ( )             _,\    ;  //
     ; ( ,_,,-~""~`""   \ (   //
      \_.'\\_            '.  /<_
       \\_)--\             \ \--\
   jgs )--\""`             )--\"`
       `""`                `""`
```
