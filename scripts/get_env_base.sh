#!/bin/bash

# take parameter
if [ -z "$1" ]; then
  echo "Usage: $0 <output_env>"
  exit 1
else
  OUTPUT_ENV="$1"
fi

# parse out project number of project with name jc-template-project
PROJECT_NAME="jc-template-project"
PROJECT_NUMBER=$(gcloud projects list --filter="name:$PROJECT_NAME" --format="value(projectNumber)")

# This script sets up the local environment variables for the project.
CRYPTO_SECRET_KEY=$(gcloud secrets versions access latest --secret="CRYPTO_SECRET_KEY" --project="$PROJECT_NUMBER")
GOOGLE_CLIENT_ID=$(gcloud secrets versions access latest --secret="GOOGLE_CLIENT_ID" --project="$PROJECT_NUMBER")
GOOGLE_CLIENT_SECRET=$(gcloud secrets versions access latest --secret="GOOGLE_CLIENT_SECRET" --project="$PROJECT_NUMBER")

# export to local.env file
echo "JC_TEMPLATE_CRYPTO_SECRET_KEY=$CRYPTO_SECRET_KEY" > "$OUTPUT_ENV"
echo "JC_TEMPLATE_GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID" >> "$OUTPUT_ENV"
echo "JC_TEMPLATE_GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET" >> "$OUTPUT_ENV"
