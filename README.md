# Credit Card API Sample

	Sample with Integration Tests, Spring Boot, Embedded MongoDB, HATEOAS, Spring REST Docs and AsciiDoctor
	
	The Credit Card API uses CodeShip and Heroku to Continuous Delivery.
	 
	Codeship is a fast and secure hosted Continuous Delivery platform that scales with your needs.
	
	Heroku is a cloud application platform that offers a new way of building and deploying web apps.
		
<img src="credit-card-api.png" alt="The Credit Card API" style="width: 800px;" />



### Model Class Diagram

<img src="model-class-diagram.png" alt="Model Class Diagram" style="width: 800px;" />



### Api Class Diagram

<img src="api-class-diagram.png" alt="Api Class Diagram" style="width: 800px;" />



### Api Sequence Diagram - Do Payment

<img src="api-sequence-diagram-do-payment.png" alt="Api Sequence Diagram - Do Payment" style="width: 800px;" />



### Api Sequence Diagram - Get Payment

<img src="api-sequence-diagram-get-payment.png" alt="Api Sequence Diagram - Get Payment" style="width: 800px;" />



### MongoDB Embebbed Config
* Database: credit-card
* Collections: payments
* url: localhost
* port: 27057

Obs.: I used [MongoDB Compass](https://www.mongodb.com/products/compass) client to access data in MongoDB.



### Spring Boot Config

See [application.properties](https://github.com/jonyfs/credit-card-api/blob/master/src/main/resources/application.properties) 



### Tools

[Spring Boot](http://projects.spring.io/spring-boot/)

[SPRING INITIALIZR](https://start.spring.io/)

[Spring REST Docs](http://projects.spring.io/spring-restdocs)

[MongoDB](https://www.mongodb.com/)

[embedmongo-spring](https://github.com/jirutka/embedmongo-spring)

[de.flapdoodle.embed.mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo)

[AsciiDoctor](http://asciidoctor.org/)



### AsciiDoctor file

See [credit-card-api.adoc](https://github.com/jonyfs/credit-card-api/blob/master/src/main/asciidoc/credit-card-api.adoc) 

### API Document Sample	
See [credit-card-api.pdf](https://github.com/jonyfs/credit-card-api/blob/master/doc/credit-card-api.pdf) 

### Continuous Delivery

[ ![Codeship Status for jonyfs/credit-card-api](https://codeship.com/projects/1aedda50-85c9-0133-cc21-12253304c6fc/status?branch=master)](https://codeship.com/projects/122440)



### How test?

	Run mvn package spring-boot:run 



### How access?	

* your local environment: http://localhost:8080/api

* online: https://creditcardapi.herokuapp.com/api


	
	

