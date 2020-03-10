#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m'

echo
echo ' $$$$$$\  $$\      $$\ $$$$$$$$\       $$$$$$$\  $$\   $$\ $$$$$$\ $$\       $$$$$$$\  '
echo '$$  __$$\ $$$\    $$$ |\____$$  |      $$  __$$\ $$ |  $$ |\_$$  _|$$ |      $$  __$$\ '
echo '$$ /  $$ |$$$$\  $$$$ |    $$  /       $$ |  $$ |$$ |  $$ |  $$ |  $$ |      $$ |  $$ |'
echo '$$$$$$$$ |$$\$$\$$ $$ |   $$  /        $$$$$$$\ |$$ |  $$ |  $$ |  $$ |      $$ |  $$ |'
echo '$$  __$$ |$$ \$$$  $$ |  $$  /         $$  __$$\ $$ |  $$ |  $$ |  $$ |      $$ |  $$ |'
echo '$$ |  $$ |$$ |\$  /$$ | $$  /          $$ |  $$ |$$ |  $$ |  $$ |  $$ |      $$ |  $$ |'
echo '$$ |  $$ |$$ | \_/ $$ |$$$$$$$$\       $$$$$$$  |\$$$$$$  |$$$$$$\ $$$$$$$$\ $$$$$$$  |'
echo '\__|  \__|\__|     \__|\________|      \_______/  \______/ \______|\________|\_______/ '
echo

function print_info {
    echo -e "$GREEN[INFO] : $1$NC"
}

function print_warn {
    echo -e "$YELLOW[WARN] : $1$NC"
}

function print_error {
    echo -e "$RED[ERROR] : $1$NC"
}

declare -a microservices=(
"config"
"discovery"
"gateway"
"auth"
"account"
"product"
)

for index in "${!microservices[@]}"
do
    service=${microservices[$index]}
    microservice="amz-microservice-${service}"

    echo
    echo -e ${GREEN}'┌───────────────────────────────────────────────────────────'${NC}
    echo -e ${GREEN}'│ BUILDING: Microservice '${microservice}'                  '${NC}
    echo -e ${GREEN}'└───────────────────────────────────────────────────────────'${NC}
    echo

    cd "$microservice/"

    {
        mvn clean install
        print_info "MAVEN: Build success for microservice '$microservice'$NC\n"
    } || {
        print_error "MAVEN: Build failed for microservice '$microservice'.$NC\n"
        exit 1
    }

    print_warn "DOCKER: Removing existing docker image '$service-service'"
    docker image rm -f ${service}-service

    {
        docker build . -t ${service}-service
        print_info "DOCKER: Successfully built image for microservice '$microservice'$NC\n"
    } || {
        print_error "DOCKER: Failed to build for microservice '$microservice'.$NC\n"
        exit 1
    }
done

echo
echo -e ${GREEN}'┌─────────────────────────────────────'${NC}
echo -e ${GREEN}'│ DONE: BUILDING DOCKER IMAGES        '${NC}
echo -e ${GREEN}'└─────────────────────────────────────'${NC}
echo
