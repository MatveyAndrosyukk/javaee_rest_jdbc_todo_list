FROM tomcat:9

COPY target/javaee_rest_jdbc_todo_list.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

#mv webapps webapps2
#mv webapps.dist/ webapps
#exit
#<?xml version="1.0" encoding="UTF-8"?>
#<tomcat-users xmlns="http://tomcat.apache.org/xml"
#              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
#              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
#              version="1.0">
#              <role rolename="manager-gui"/>
#              <user username="tomcat" password="tomcat" roles="manager-gui"/>
#</tomcat-users>


