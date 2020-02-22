# Read Me : GraphQL Course

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Introduction

This project (very simple) was made for my students and curious people about GraphQL (Java).

## Get Started

1) Clone the project and checkout to the master branch
2) mvn clean install
3) Execute the main application (AppStarter)
4) Launch Postman or client REST equivalent / curl
5) Create a request POST to localhost:8080/api/public/query. The body request have to contain
the request. You can find some examples of request to the file [examples-requests.graphqls](https://github.com/yassineazimani/GraphQLCourse/blob/master/examples-requests.graphqls)

Example with CURL : 

curl \
  -X POST \
  -H "Content-Type: application/json" \
  --data '{ "query": "{ allCities{ id, name }  }" }' \
  http://localhost:8080/api/public/query
  
Response : 

{"allCities":[{"id":1,"name":"Salon"}\,{"id":2,"name":"Montpellier"}\,{"id":3,"name":"Bikini-Bottom"}]}

## Software architecture

![alt text](https://github.com/yassineazimani/GraphQLCourse/blob/master/images/architecture_software_graphql.png?raw=true "Software architecture")

[Zoom Image](https://github.com/yassineazimani/GraphQLCourse/blob/master/images/architecture_software_graphql.png)

## License

Apache License, Version 2.0

https://opensource.org/licenses/Apache-2.0

This projects uses Mockito : [License Mockito](https://github.com/mockito/mockito/wiki/License), 

AssertJ : [License AssertJ](https://github.com/joel-costigliola/assertj-core/blob/master/LICENSE.txt), 

Lombok : [License MIT Lombok](https://projectlombok.org/), 

Jackson : [License Jackson](https://github.com/FasterXML/jackson-core/blob/master/src/main/resources/META-INF/LICENSE), 

Log4J 2: [License Log4J 2](https://logging.apache.org/log4j/2.x/license.html), 

Apache commons (lang3, codec, collections, httpcomponents): [License Apache Commons](https://commons.apache.org/proper/commons-bsf/license.html), 