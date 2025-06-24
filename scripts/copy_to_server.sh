#!/bin/bash

# prompt for these variables
read -p "Enter SSH username: " USER
read -p "Enter SSH server address: " SERVER
read -p "Enter SSH port (default is 22): " PORT
if [ -z "$PORT" ]; then
  PORT=22  # Default SSH port
fi
read -p "Enter domain for the application: " DOMAIN

if [ -z "$USER" ]; then
  echo "Error: SSH_USER environment variable is not set."
  exit 1
fi
if [ -z "$SERVER" ]; then
  echo "Error: SSH_SERVER environment variable is not set."
  exit 1
fi
if [ -z "$DOMAIN" ]; then
  echo "Error: DOMAIN environment variable is not set."
  exit 1
fi

# variables
PROJECT_DIR="~/docker-apps/jc-kotlin-template"

# prepare env file (currently re-using local env file, update with deployed env file differences as needed)
./scripts/get_local_env.sh
cp local.env .env

# make non-local changes to .env file
# add dns domain to env file
echo "JC_TEMPLATE_DOMAIN=$DOMAIN" >> .env

# create list of resources to copy
RESOURCES=(
  "docker-compose.yml"
  ".env"
  "scripts/start.sh"
)

# check is resources exist
for resource in "${RESOURCES[@]}"; do
  if [ ! -f "$resource" ]; then
    echo "Error: $resource file not found."
    exit 1
  fi
done

# create backups of existing files on the server
echo "Creating backups of existing files on the server..."
ssh -p "$PORT" "$USER@$SERVER" "mkdir -p $PROJECT_DIR/backup && \
  cp $PROJECT_DIR/docker-compose.yml $PROJECT_DIR/backup/docker-compose.yml.bak && \
  cp $PROJECT_DIR/.env $PROJECT_DIR/backup/.env.bak && \
  cp $PROJECT_DIR/start.sh $PROJECT_DIR/backup/start.sh.bak"

# copy resources to server
echo "copying [${RESOURCES[@]/#/}] to $USER@$SERVER:$PORT"
scp -P "$PORT" -r "${RESOURCES[@]/#/}" "$USER@$SERVER:$PROJECT_DIR/"

