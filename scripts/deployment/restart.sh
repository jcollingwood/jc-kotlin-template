#!/bin/bash

# This script stops and then starts the application.

echo "Restarting the application..."

# Stop the application
./stop.sh

# Start the application
./start.sh

echo "Application restarted."
