FROM openjdk:21
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/ordermicroservice-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
