FROM openjdk:17
EXPOSE 8084
ADD target/spring_docker_app.jar spring_docker_app.jar
ENTRYPOINT [ "java","-jar","/spring_docker_app.jar" ]