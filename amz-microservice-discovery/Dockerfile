FROM openjdk
MAINTAINER Aram Kirakosyan <aramkirakosyan@gmail.com>
ARG JAR_VERSION=1.0-SNAPSHOT
ARG JAR_FILE=target/amz-microservice-discovery-${JAR_VERSION}.jar
ADD ${JAR_FILE} discovery-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/discovery-service.jar"]
EXPOSE 8000