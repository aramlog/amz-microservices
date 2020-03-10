# Microservices Project with Spring Cloud and Netflix Integration

The project demonstrating the most interesting features of Spring Cloud and Netflix OSS.
The project is about a simple shopping cart solution with few core features.

## Product Features
Below are listed the product key features:
- Register an account (either customer or seller)
- Update account
- Remove account
- Find filtered accounts by specified criteria
- Authenticate with username/password, issue JWT token signed using private certificate
- Authorization with JWT token
- Refresh JWT token on expression
- Add product in stock
- Search product by various criteria

## Project Infrastructure
The project is multi-module maven and spring boot project. The project consists of the following modules:
- amz-common
  - amz-common-client
  - amz-common-object
  - amz-common-server
- amz-microservice-config
- amz-microservice-discovery
- amz-microservice-gateway
- amz-microservice-auth
  - amz-microservice-auth-api
  - amz-microservice-auth-client
  - amz-microservice-auth-server
- amz-microservice-account
  - amz-microservice-account-api
  - amz-microservice-account-client
  - amz-microservice-account-server
- amz-microservice-product
  - amz-microservice-product-api
  - amz-microservice-product-client
  - amz-microservice-product-server
  
Common modules are used in micoroservices to share the common functionality as authentication/authorization, common configs and DTOs, error handling etc.
Each microservice (except config, discovery, and gateway) has similar modularity and architecture. 
- xxx-api: expose the Rest Service endpoints interfaces along with DTOs, and provide swagger documentations.
- xxx-client: contains feign clients and mocks for integration test.
- xxx-server: spring boot application Rest Controllers implementations and business logic.

## Get Started
In order to get started with the project make sure to have installed Maven 3+, JDK 8+, PostgresSQL 9+ and Docker.

### Step 1: Create Postgres Schemas
In the postgres database system create 3 follwing schemas:
1. amz_auth   
2. amz_account
3. amz_product

```
create schema amz_auth;
```

### Step 2: Update Datasource Configs
To be able to connect to postgres database update the the datasource configs in the "amz-microservices/amz-microservice-config/src/main/resources/config/" directory in the following files:
1. amz-microservice-auth.yml
2. amz-microservice-account.yml
3. amz-microservice-product.yml

### Step 3: Build Project
Run maven clean and install goals to build all necessary artifacts.
Execute the following command in the project root directory:

```
mvn clean install
```

### Step 4: Run Spring Boot
Run SpringBoot applications manually by executing the following command in terminal for each microservice:
```
mvn spring-boot:run -Drun.profiles=local
```

>*Note\*: No need to specify profile for 'amz-microservice-config' application*

>*Note\*: It's very important to run 'amz-microservice-config' first, then 'amz-microservice-discovery' and then others*

In the alternative of running microservices separately, there is a shell script to automatically run all microservices.
```
./local.sh
```

## Docker Containerization
All microservices has a Dockerfile to create appropriate docker images. Here are the commands that needs to be executed for building an image and running container:
```
docker build . -t config-service
```
```
docker run --net=host --name config-container config-service:latest 
```

To automatically orchestrate all microservices use the following scripts:
```
./build.sh
```
```
./deploy.sh 
```