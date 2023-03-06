#!/bin/bash

POSTGRES_USER=tcc_match
POSTGRES_PASSWORD=c02Ftf2YeSLazlQPPvjgSBGAs7BvZHWoeEUtubCOfSkUcrI7XoS2ClIN68tNcraT

function install_docker() {
  curl https://get.docker.com | bash
  sudo usermod -aG docker $USER
  newgrp docker
}

function instantiate_psql_db() {
  docker run -d -e POSTGRES_USER=$POSTGRES_USER \
                -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
                --name tcc-match-db \
                -v tcc-match-data:/var/lib/postgresql/data \
                -p 5433:5432 \
                   postgres:11
}

docker &>/dev/null

if [ $? -ne 0 ]; then
  echo "Docker não encontrado, instalando..."
  install_docker
fi

echo "Subindo banco de dados"
instantiate_psql_db 2>/dev/null

if [ $? -ne 0 ]; then
  docker start tcc-match-db
fi
