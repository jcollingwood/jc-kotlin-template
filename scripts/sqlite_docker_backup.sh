#!/bin/bash

set -eo pipefail

echo "Starting database backup..."
# validate required env variables
if [ -z "$GCP_BUCKET_NAME" ]; then
  echo "Error: GCP_BUCKET_NAME environment variable is not set."
  exit 1
fi

TIMESTAMP=$(date +%Y-%m-%d_%H-%M-%S)
BACKUP_NAME="template_db_backup_$TIMESTAMP.db"

BACKUP_LOCATION="/tmp/$BACKUP_NAME"
DB_LOCATION="/db/template_db.db"

# Create a backup of the database file
cp "$DB_LOCATION" "$BACKUP_LOCATION"

# Upload the backup to GCS
gcloud storage cp --project jc-kotlin-project "$BACKUP_LOCATION" "gs://$GCP_BUCKET_NAME/$BACKUP_NAME"

# Remove the local backup file
rm "$BACKUP_LOCATION"
echo "Completed database backup..."

