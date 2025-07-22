#!/bin/bash

# take domain as parameter
if [ -z "$1" ]; then
  echo "Usage: $0 <domain>"
  exit 1
else
  DOMAIN="$1"
fi

OUTPUT_ENV=".env"
. ./scripts/get_env_base.sh "$OUTPUT_ENV"

echo "JC_TEMPLATE_DOMAIN=$DOMAIN" >> .env
echo "export JC_TEMPLATE_SQLITE_CONN_URL=\"jdbc:sqlite:./db/template_db.db\"" >> "$OUTPUT_ENV"
