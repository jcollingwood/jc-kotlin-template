#!/bin/bash

# prompt for these variables
# Use environment variables as defaults if they are set

# Prompt for SSH username
if [ -n "$SSH_USER" ]; then
    read -p "Enter SSH username [default: $SSH_USER]: " input_user
    USER=${input_user:-$SSH_USER}
else
    read -p "Enter SSH username: " USER
fi

# Prompt for SSH server address
if [ -n "$SSH_SERVER" ]; then
    read -p "Enter SSH server address [default: $SSH_SERVER]: " input_server
    SERVER=${input_server:-$SSH_SERVER}
else
    read -p "Enter SSH server address: " SERVER
fi

# Prompt for SSH port
if [ -n "$SSH_PORT" ]; then
    read -p "Enter SSH port [default: $SSH_PORT]: " input_port
    PORT=${input_port:-$SSH_PORT}
else
    read -p "Enter SSH port [default: 22]: " input_port
    PORT=${input_port:-22}
fi

# Prompt for domain
if [ -n "$JC_TEMPLATE_DOMAIN" ]; then
    read -p "Enter domain for the application [default: $JC_TEMPLATE_DOMAIN]: " input_domain
    DOMAIN=${input_domain:-$JC_TEMPLATE_DOMAIN}
else
    read -p "Enter domain for the application: " DOMAIN
fi

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
  "docker-compose.sqlite-backup.yml"
  ".env"
  "scripts/deployment/start.sh"
  "scripts/deployment/stop.sh"
  "scripts/deployment/restart.sh"
  "scripts/deployment/sqlite_backup.sh"
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
backup_command="mkdir -p $PROJECT_DIR/backup"
for resource in "${RESOURCES[@]}"; do
  filename=$(basename "$resource")
  backup_command+=" && cp $PROJECT_DIR/$filename $PROJECT_DIR/backup/${filename}.bak"
done
ssh -p "$PORT" "$USER@$SERVER" "$backup_command"

# copy resources to server
echo "copying [${RESOURCES[@]/#/}] to $USER@$SERVER:$PORT"
scp -P "$PORT" -r "${RESOURCES[@]/#/}" "$USER@$SERVER:$PROJECT_DIR/"

