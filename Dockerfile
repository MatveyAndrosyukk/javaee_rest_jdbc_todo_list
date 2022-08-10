#FROM maven:3.6.3-jdk-11
#
#COPY ./ ./
#
#RUN mvn clean package
#
#CMD ["java", "-war","target/javaee_rest_jdbc_todo_list.war"]


FROM tomcat

COPY target/javaee_rest_jdbc_todo_list.war /usr/local/tomcat/webapps/