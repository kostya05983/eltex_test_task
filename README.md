## Getting Started
Clone the project from repository 

###Test
The project was running with jdk 10.0.1 and gradle 4.6
but it is compatible with jdk 9
### Prerequisites

You need Jdk 10.0.1, Gradle 4.6, MongoDB

Gradle : https://gradle.org/install/

Jdk 10.0.1 : http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html

MongoDB : https://www.mongodb.com/

### Settings
You can change Port,baseDir,APP_BASE of Tomcat in src/main/java/Server/Main.java

You can configure log4j in src/main/resources/log4j.properties

You can configure the NameOfDB and CollectionName and Key(field) in src/main/java/Base/Connect

Also you can configure your own Key in src/main/java/API/HttpTemperature for YandexWeather and Geocoordinating

### Installing
Run the Base using MongoDB, the NameOfBase: see the previous paragraph

Just run ./run.sh in bash

## Built With

* [Vaadin](https://vaadin.com/framework) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
* [Spring](https://spring.io/) - use to configure Servlets
* [Tomcat](http://tomcat.apache.org/) - web-server

## Authors

**kostya05983(RustLife)**
you can write me on: kostya05983@mail.ru


