#!/bin/bash

# parse out project number of project with name jc-template-project
PROJECT_NAME="jc-template-project"
PROJECT_NUMBER=$(gcloud projects list --filter="name:$PROJECT_NAME" --format="value(projectNumber)")

# This script sets up the local environment variables for the project.
GOOGLE_CLIENT_ID=$(gcloud secrets versions access latest --secret="GOOGLE_CLIENT_ID" --project="$PROJECT_NUMBER")
GOOGLE_CLIENT_SECRET=$(gcloud secrets versions access latest --secret="GOOGLE_CLIENT_ID" --project="$PROJECT_NUMBER")

# export to local.env file
echo "JC_TEMPLATE_DOMAIN=http://localhost:3333" > local.env
echo "JC_TEMPLATE_GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID" > local.env
echo "JC_TEMPLATE_GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET" >> local.env
