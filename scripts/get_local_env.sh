#!/bin/bash

OUTPUT_ENV="local.env"
. ./scripts/get_env_base.sh "$OUTPUT_ENV"

echo "JC_TEMPLATE_DOMAIN=http://localhost:3333" >> "$OUTPUT_ENV"
echo "export JC_TEMPLATE_SQLITE_CONN_URL=\"jdbc:sqlite:../db/template_db.db\"" >> "$OUTPUT_ENV"
