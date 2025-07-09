#!/bin/bash

PROJECT_NAME="jc-common"
PROJECT_NUMBER=$(gcloud projects list --filter="name:$PROJECT_NAME" --format="value(projectNumber)")
GHCR_TOKEN=$(gcloud secrets versions access latest --secret="GH_CR_READONLY_TOKEN" --project="$PROJECT_NUMBER")

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
