# Introduction
This is a spring boot maven based application. It exposes a rest api which can be used to fetch ip address details like 
```http://localhost:8080/info?ip=1.2.7.3```. 
It uses embedded tomcat web server which runs on default port 8080. It also caches the result using redis cache.

# Setup:
 Since it uses embedded redis and h2 db, it doesn't require any manual configuration. For more information about configuration of db, redis, or other;
 see the ```application.properties``` and ```pom.xml```. 
 
# TODO:
  Add more unit test to cover all the condition in service.

  *Note: current test are only for controller, validation and cache.*