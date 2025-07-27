# Feature: Automated Session Pruning Job

- **Status:** Done
- **Author:** joel
- **Date:** 2025-07-24

## 1. Goal

To automatically remove old and expired user session records from the database to maintain data hygiene and security.

## 2. Description

This feature implements an automated background job that runs on a schedule within the server. It is responsible for querying the database for user sessions that have passed their expiration date and deleting them. This ensures the `sessions` table does not grow indefinitely and helps enforce security by removing stale sessions.

*As a system administrator, I want expired user sessions to be automatically pruned so that the database is kept clean and the attack surface is minimized.*

## 3. Tasks

- [x] Create a scheduled job runner within the server application.
- [x] Implement the logic to identify and delete expired user sessions.
- [x] Configure the job to run at a regular, predefined interval.
- [x] Add logging to provide visibility into the pruning process.

## 4. Technical Notes & Considerations

- The job is implemented in the `SessionPruneJob.kt` file.
- It uses a scheduled executor service to run periodically without blocking the main server threads.

## 5. Open Questions

- None
