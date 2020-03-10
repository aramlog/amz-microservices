#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m'

echo
echo ' $$$$$$\  $$\      $$\ $$$$$$$$\       $$$$$$$\  $$$$$$$$\ $$$$$$$\  $$\       $$$$$$\ $$\     $$\ '
echo '$$  __$$\ $$$\    $$$ |\____$$  |      $$  __$$\ $$  _____|$$  __$$\ $$ |     $$  __$$\\$$\   $$  |'
echo '$$ /  $$ |$$$$\  $$$$ |    $$  /       $$ |  $$ |$$ |      $$ |  $$ |$$ |     $$ /  $$ |\$$\ $$  / '
echo '$$$$$$$$ |$$\$$\$$ $$ |   $$  /        $$ |  $$ |$$$$$\    $$$$$$$  |$$ |     $$ |  $$ | \$$$$  /  '
echo '$$  __$$ |$$ \$$$  $$ |  $$  /         $$ |  $$ |$$  __|   $$  ____/ $$ |     $$ |  $$ |  \$$  /   '
echo '$$ |  $$ |$$ |\$  /$$ | $$  /          $$ |  $$ |$$ |      $$ |      $$ |     $$ |  $$ |   $$ |    '
echo '$$ |  $$ |$$ | \_/ $$ |$$$$$$$$\       $$$$$$$  |$$$$$$$$\ $$ |      $$$$$$$$\ $$$$$$  |   $$ |    '
echo '\__|  \__|\__|     \__|\________|      \_______/ \________|\__|      \________|\______/    \__|    '
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
    echo -e ${GREEN}'│ DEPLOYING: Microservice '${microservice}'                '${NC}
    echo -e ${GREEN}'└───────────────────────────────────────────────────────────'${NC}
    echo

    print_warn "DOCKER: Removing existing docker container '$service-container'"
    docker stop ${service}-container
    docker container rm -f ${service}-container

    {
        docker run --net=host --name ${service}-container ${service}-service:latest
        print_info "DOCKER: Successfully run microservice '$microservice' in docker '$service-container' container$NC\n"
    } || {
        print_error "DOCKER: Failed to deploy microservice '$microservice'.$NC\n"
        exit 1
    }
done

echo
echo -e ${GREEN}'┌─────────────────────────────────────'${NC}
echo -e ${GREEN}'│ DONE: DEPLOYING DOCKER CONTAINERS   '${NC}
echo -e ${GREEN}'└─────────────────────────────────────'${NC}
echo
