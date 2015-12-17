# Credit Card Api Sample

	Sample with TDD, Spring Boot, Embedded MongoDB, HATEOAS, Spring REST Docs

### Model Class Diagram

<img src="model-class-diagram.png" alt="Model Class Diagram" style="width: 800px;" />



### Api Class Diagram

<img src="api-class-diagram.png" alt="Api Class Diagram" style="width: 800px;" />


### MongoDB Embebbed Config
* Database: credit-card
* Collections: payments
* url: localhost
* port: 12345

Obs.: I used [RoboMongo](http://robomongo.org/) client to access data in MongoDB.


### Spring Boot Config

See [application.properties](https://github.com/jonyfs/credit-card-api/blob/master/src/main/resources/application.properties) 


### Tools

[Spring Boot](http://projects.spring.io/spring-boot/)

[SPRING INITIALIZR](https://start.spring.io/)

[Spring REST Docs](http://projects.spring.io/spring-restdocs)

[MongoDB](https://www.mongodb.com/)

[embedmongo-spring](https://github.com/jirutka/embedmongo-spring)

[de.flapdoodle.embed.mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo)


### AsciiDoc 

See [credit-card-api.adoc](https://github.com/jonyfs/credit-card-api/blob/master/src/main/asciidoc/credit-card-api.adoc) 

### How test?

	Run mvn package spring-boot:run 


### How access?	

	http://localhost:8080/
	