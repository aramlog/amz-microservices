FROM openjdk
MAINTAINER Aram Kirakosyan <aramkirakosyan@gmail.com>
ARG JAR_VERSION=1.0-SNAPSHOT
ARG JAR_FILE=target/amz-microservice-config-${JAR_VERSION}.jar
ADD ${JAR_FILE} config-service.jar
ENTRYPOINT ["java", "-jar", "/config-service.jar"]
EXPOSE 8088