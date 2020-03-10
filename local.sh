#!/bin/bash

GREEN='\033[0;32m'
NC='\033[0m'

echo -e ${GREEN}'│ PROCESSING: Microservice 'amz-microservice-config''${NC}
cd "amz-microservice-config/"
mvn clean install
mvn spring-boot:run &

echo -e ${GREEN}'│ PROCESSING: Microservice 'amz-microservice-discovery''${NC}
cd "amz-microservice-discovery/"
mvn clean install
mvn spring-boot:run -Drun.profiles=local &

echo -e ${GREEN}'│ PROCESSING: Microservice 'amz-microservice-discovery''${NC}
cd "amz-microservice-gateway/"
mvn clean install
mvn spring-boot:run -Drun.profiles=local &

declare -a microservices=(
"auth"
"account"
"product"
)

for index in "${!microservices[@]}"
do
    service=${microservices[$index]}
    microservice="amz-microservice-${service}"

    echo -e ${GREEN}'│ PROCESSING: Microservice '${microservice}''${NC}

    cd "${microservice}/"
    mvn clean install

    cd "${microservice}-server"
    mvn spring-boot:run -Drun.profiles=local &

    cd ../../
done