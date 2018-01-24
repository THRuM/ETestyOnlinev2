# ETestyOnlinev2
This is an application for single-choice tests.  

For test and fun purposes application is hosted on Heroku: [Link](http://e-testyonline.herokuapp.com)

## Prerequisites
For proper functioning MongoDB database and SMTP server are needed, fill the `config.properties` in `/src/main/resources` with the proper values.
Originally the mLab was used as a hosting for MongoDB and Gmail as SMTP server.

TODO: Polulate DB

## Getting Started
This is maven project, check out it and run:
```
$ mvn clean install
```

## Built With
* Spring MVC - Web framework
* Spring Security - Security framework
* Spring Data MongoDB - MongoDB integration
* MongoDB - Persistance layer
* Apache Tiles - JSP Engine
* Semantic UI - CSS Framework
* Maven - Dependency Management


Author
Maciej Cyrka

License
This project is licensed under the MIT License.

## Inspiration
Application was created as a thesis, name of the thesis "Web application for learning support made in Java EE, Spring MVC".
