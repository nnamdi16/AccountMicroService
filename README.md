# NOTIFICATION MICROSERVICE

This microservice is in charge of managing the accounts

## How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository
* Create Mysql database
  
    - run `com/nnamdi/transaction/data/UserRoles.sql`
    
* Make sure you are using JDK 1.8 and Gradle 5.x
* Change mysql username and password as per your installation
    + open `src/main/resources/application.properties`
    + change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation
* You can build the project and run the tests by running ```gradle clean build```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar build/libs/account-0.0.1-SNAPSHOT.jar
```


Once the application runs you should see something like this

```
2017-08-29 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
2017-08-29 17:31:23.097  INFO 19387 --- [           main] com.khoubyari.example.Application        : Started Application in 22.285 seconds (JVM running for 23.032)
```

## About the Service

The service is just a simple REST service for creating accounts. It uses a mysql database to store the data. You can also use other relational database like PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in ```package com.nnamdi.account.controller``` on **port 9000**. (see below)

More interestingly, you can start calling some operational endpoints (see full list below) like ```/signup``` and ```/login``` (these are available on **port 9000**)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backend RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the same patterns as the sample service.


Here are some endpoints you can call:

### Create an account

```
http://localhost:9000/api/auth/signup
```

### To login to the account

```
http://localhost:9000/api/auth/login
```

### Get list of accounts or account info

```
http://localhost:9000/api/accounts/all
http://localhost:9000/account/{id}

```


### Create an account
```
POST /api/v1.0/notifier/notify/{channelType}
Accept: application/json
Content-Type: application/json

{
    "username":"reloadly1234",
    "email": "znwabuokei@gmail.com",
    "address": "Lagos State",
    "phoneNumber": "+234704599769959",
    "password":"12345893",
    "name": "Basky Clan"
}

RESPONSE: HTTP 201 (Created)
{
    "accountId": 13,
    "username": "reloadly1234",
    "name": "Basky Clan",
    "address": "Lagos State",
    "phoneNumber": "+234704599769959",
    "email": "znwabuokei@gmail.com",
    "password": "$2a$10$XDRjfx7JYeP4g0EkKfi.UeqEAuQWIYTM8xB4JKHKvylH/9QEqufS2",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:9000/api/accounts/account/13"
        },
        "accounts": {
            "href": "http://localhost:9000/api/accounts/all"
        }
    }
}
```



### Login to account

```
POST /api/auth/login
Accept: application/json
Content-Type: application/json

{
    "username":"Bolaji12344",
    "password":"12345893"
   
}

RESPONSE: HTTP 200 
{
    "id": 12,
    "username": "Bolaji12344",
    "email": "bodennamdi30@yahoo.ca",
    "roles": [
        "ROLE_USER"
    ],
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCb2xhamkxMjM0NCIsImlhdCI6MTYxNzE0MDcwMCwiZXhwIjoxNjE3MjI3MTAwfQ.DsiyDdZrgsFwA9BlqsF_xVjAiiP-GZCYX11MNUP_BvGuiPkyZAwMLoGiSkn1Eh1liS4szIK0FPMX8aPsN6jOMA",
    "tokenType": "Bearer"
}
```



### To view Swagger 2 API docs

Run the server and browse to localhost:9000/swagger-ui.html

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:



# Area of Improvement

**1. Setting up notification on successful payment - Partly completed**

**2. Implementing JWT Authentication**

**2. Improve Code documentation**


# Questions and Comments: nwabuokeinnamdi19@gmail.com

