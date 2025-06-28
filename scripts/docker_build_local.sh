#!/bin/bash

echo "Building project with Gradle..."
./gradlew clean build -p server
if [ $? -ne 0 ]; then
    echo "Gradle build failed. Exiting."
    exit 1
fi

echo "Building Docker image for jc-kotlin-template:local"
docker buildx build -t jc-kotlin-template:local -f server/Dockerfile .
if [ $? -ne 0 ]; then
    echo "Docker build failed. Exiting."
    exit 1
fi

echo "Docker image jc-kotlin-template:local built successfully."
