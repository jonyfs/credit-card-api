# Credit Card Api Sample

	Sample with TDD, Spring Boot, Embedded MongoDB, HATEOAS, HAL Browser and Spring REST Docs

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

[Spring Data Rest HAL Browser](http://docs.spring.io/spring-data/rest/docs/current/reference/html/#_the_hal_browser)

[Spring REST Docs](http://projects.spring.io/spring-restdocs)

[MongoDB](https://www.mongodb.com/)

[embedmongo-spring](https://github.com/jirutka/embedmongo-spring)

[de.flapdoodle.embed.mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo)