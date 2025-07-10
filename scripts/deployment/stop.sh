#!/bin/sh

echo "Stopping database backup cron job..."
(crontab -l | grep -v 'sqlite_backup.sh') | crontab -
echo "Cron job removed successfully."

echo "Stopping the Docker Compose services..."
docker compose down
echo "Services stopped successfully."

