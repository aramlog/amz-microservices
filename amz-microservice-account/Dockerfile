FROM openjdk
MAINTAINER Aram Kirakosyan <aramkirakosyan@gmail.com>
ARG JAR_VERSION=1.0-SNAPSHOT
ARG JAR_FILE=amz-microservice-account-server/target/amz-microservice-account-server-${JAR_VERSION}.jar
ADD ${JAR_FILE} account-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/account-service.jar"]
EXPOSE 8082