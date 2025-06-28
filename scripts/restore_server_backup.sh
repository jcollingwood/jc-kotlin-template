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

if [ -z "$USER" ]; then
  echo "Error: SSH_USER environment variable is not set."
  exit 1
fi
if [ -z "$SERVER" ]; then
  echo "Error: SSH_SERVER environment variable is not set."
  exit 1
fi

# variables
PROJECT_DIR="~/docker-apps/jc-kotlin-template"
BACKUP_DIR="$PROJECT_DIR/backup"

# create list of resources to restore
RESOURCES=(
  "docker-compose.yml"
  ".env"
  "start.sh"
  "stop.sh"
  "restart.sh"
)

echo "Restoring files on the server..."
restore_command=""
for resource in "${RESOURCES[@]}"; do
  restore_command+="mv $BACKUP_DIR/${resource}.bak $PROJECT_DIR/$resource && "
done
# Remove the trailing ' && '
restore_command=${restore_command%????}

ssh -p "$PORT" "$USER@$SERVER" "$restore_command"

echo "Restore complete."
