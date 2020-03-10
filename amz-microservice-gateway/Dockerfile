FROM openjdk
MAINTAINER Aram Kirakosyan <aramkirakosyan@gmail.com>
ARG JAR_VERSION=1.0-SNAPSHOT
ARG JAR_FILE=target/amz-microservice-gateway-${JAR_VERSION}.jar
ADD ${JAR_FILE} gateway-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/gateway-service.jar"]
EXPOSE 8001