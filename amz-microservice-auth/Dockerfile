FROM openjdk
MAINTAINER Aram Kirakosyan <aramkirakosyan@gmail.com>
ARG JAR_VERSION=1.0-SNAPSHOT
ARG JAR_FILE=amz-microservice-auth-server/target/amz-microservice-auth-server-${JAR_VERSION}.jar
ADD ${JAR_FILE} auth-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/auth-service.jar"]
EXPOSE 8081