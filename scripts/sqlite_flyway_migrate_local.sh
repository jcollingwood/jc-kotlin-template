#!/bin/bash
# This script is used to run Flyway migrations locally.
set -e

# check that sqlite3 is installed
if ! command -v sqlite3 &> /dev/null; then
    echo "sqlite3 could not be found. Please install it to run this script."
    exit 1
fi

# check that template_db sqlite db exists under db project
if [ ! -f "db/template_db.sqlite" ]; then
    echo "template_db.sqlite does not exist in the db directory. creating it now."
    sqlite3 db/template_db.db ".tables"
fi

# run gradle flyway migrate
./gradlew flywayMigrate -p db
if [ $? -ne 0 ]; then
    echo "Flyway migration failed. Please check the logs for details."
    exit 1
fi

# check that the migration was successful and basic tables exist
sqliteTables=$(sqlite3 db/template_db.db ".tables")
echo "Current tables in the database: $sqliteTables"
if [[ ! $sqliteTables =~ "flyway_schema_history" ]] || [[ ! $sqliteTables =~ "user_sessions" ]]; then
    echo "Migration did not create the expected tables. Please check the migration scripts."
    exit 1
fi