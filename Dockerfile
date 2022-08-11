FROM tomcat:9

COPY target/javaee_rest_jdbc_todo_list.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

#mv webapps webapps2
#mv webapps.dist/ webapps
#exit