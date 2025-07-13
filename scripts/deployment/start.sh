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

echo "Setting database backup cron job..."
# Set up a cron job to run the backup script every day at 2 AM
CRON_JOB="0 2 * * * /bin/bash sqlite_backup.sh >> /var/log/sqlite_backup.log 2>&1"
(crontab -l 2>/dev/null; echo "$CRON_JOB") | crontab -
echo "Cron job set successfully."
