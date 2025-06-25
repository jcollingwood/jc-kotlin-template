#!/bin/bash

echo "Building Docker image for jc-kotlin-template:local"
docker buildx build -t jc-kotlin-template:local -f server/Dockerfile .
echo "Docker image jc-kotlin-template:local built successfully."
