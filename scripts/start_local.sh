#!/bin/sh

echo "Starting local development environment..."
docker compose -f docker-compose.yml -f docker-compose-local.yml up -d
echo "Local development environment started."

