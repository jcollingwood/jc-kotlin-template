#!/bin/bash

# take input for version
if [ -z "$1" ]; then
  curr_version=$(grep 'image: ghcr.io/jcollingwood/jc-kotlin-template/server:' docker-compose.yml | awk -F ':' '{print $NF}')
  echo "Version not provided. Please enter a version. (note: current version defined in docker-compose is $curr_version)"
  read -p "Enter version: " version
fi

version=${1:-$curr_version}
docker buildx build --platform linux/arm/v7,linux/amd64 -t ghcr.io/jcollingwood/jc-kotlin-template/server:$version -f server/Dockerfile .

if [ $? -ne 0 ]; then
  echo "Docker build failed."
  exit 1
fi

# push the image to GitHub Container Registry
docker push ghcr.io/jcollingwood/jc-kotlin-template/server:$version

# update docker-compose.yml with the new version
sed -i "s|image: ghcr.io/jcollingwood/jc-kotlin-template/server:.*|image: ghcr.io/jcollingwood/jc-kotlin-template/server:$version|" docker-compose.yml

