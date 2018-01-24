# ETestyOnlinev2
This is an application for single-choice tests.  

For test and fun purposes application is hosted on Heroku: [Link](http://e-testyonline.herokuapp.com)

## Prerequisites
For proper functioning MongoDB database and SMTP server are needed, fill the `config.properties` in `/src/main/resources` with the proper values.
Originally the mLab was used as a hosting for MongoDB and Gmail as SMTP server.

## Database preparation
Unzip file `etestyonline.zip` and run command:
```
mongorestore -h <host> -d etestyonline -u <user> -p <password> etestyonline
```

This will set up sample user with ADMIN rights: admin@etestyonline.pl : zaq12wsx

## Getting Started
This is maven project, check out it and run:
```
$ mvn clean install
```

## Built With
* Spring MVC - Web framework
* Spring Security - Security framework
* Spring Data MongoDB - MongoDB integration
* MongoDB - Persistence layer
* Apache Tiles - Templating framework 
* Semantic UI - CSS framework
* Maven - Dependency Management


Author
Maciej Cyrka

License
This project is licensed under the MIT License.

## Inspiration
Application was created as a thesis, name of the thesis "Web application for learning support made in Java EE, Spring MVC".
