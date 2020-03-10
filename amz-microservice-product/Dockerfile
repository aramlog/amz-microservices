FROM openjdk
MAINTAINER Aram Kirakosyan <aramkirakosyan@gmail.com>
ARG JAR_VERSION=1.0-SNAPSHOT
ARG JAR_FILE=amz-microservice-product-server/target/amz-microservice-product-server-${JAR_VERSION}.jar
ADD ${JAR_FILE} product-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/product-service.jar"]
EXPOSE 8083