# TaskService
Hello, this is a project without using frameworks. This is task service, where you can make all operations with 
 tasks. Also, you can make all REST operations with tasks. There is full authentication with 
 password encoding
## Used technologies
JavaEE, MySQL, JSP, CSS, Postman, Docker
## Requirements
+ Maven
+ Git  
+ Docker  
+ Docker-compose  
## Run
```
$ git clone https://github.com/MatveyAndrosyukk/javaee_rest_jdbc_todo_list.git
$ cd javaee_rest_jdbc_todo_list
$ mvn clean package
$ docker pull tomcat:9
$ docker build -t task_api .  
$ docker-compose up -d     
```
## Stop
Press **Ctrl+C** to stop containers running

## Basic Workflow
1. Move http://localhost:8080/javaee_rest_jdbc_todo_list/registration and register here. 
2. Go to login page and log in. 
4. Move to a tasks page and use site with pleasure.  

## Additional information
1. If you don't want to register, you can use one of pre created users to log in:
+ admin - admin
+ m - m
2. MySQL container can create database for a long time, so you need to wait some time, and may be restart application container when it will be created.
