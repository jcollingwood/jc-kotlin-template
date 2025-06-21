#!/bin/bash

# check that container registry token is set
if [ -z "$GHCR_TOKEN" ]; then
  echo "Error: GHCR_TOKEN environment variable is not set."
  exit 1
fi

echo "Starting jc-kotlin-template application..."

echo "Authenticating with container registry..."

docker login ghcr.io -u jcollingwood -p "$GHCR_TOKEN"

docker compose up -d

echo "Application started successfully."

docker compose ps
