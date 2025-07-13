#!/bin/bash

set -eo pipefail

echo "Starting database backup..."

# spins up image with volume and dumps db file for backup
echo "Getting database from docker volume..."
docker compose -f docker-compose.sqlite-backup.yml up

GCP_BUCKET_NAME="jc-template-project-db-backup"
TIMESTAMP=$(date +%Y-%m-%d_%H-%M-%S)
BACKUP_NAME="template_db_backup_$TIMESTAMP.db"

BACKUP_LOCATION="./tmp/$BACKUP_NAME"
DB_LOCATION="./tmp/template_db.db"

# Create a backup of the database file
cp "$DB_LOCATION" "$BACKUP_LOCATION"

# Upload the backup to GCS
echo "Pushing backup to cloud storage..."
gcloud storage cp --project jc-kotlin-project "$BACKUP_LOCATION" "gs://$GCP_BUCKET_NAME/$BACKUP_NAME"

# Remove the local backup and temp db file
rm "$BACKUP_LOCATION" "$DB_LOCATION"
echo "Completed database backup..."

