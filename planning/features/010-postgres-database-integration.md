# Feature: Postgres Database Integration

- **Status:** To Do
- **Author:** joel
- **Date:** 2025-07-25

## 1. Goal

To add support for PostgreSQL as a database option for the application, including production-ready configuration and migration scripts.

## 2. Description

This feature will extend the existing database integration to support PostgreSQL. This will involve adding the necessary drivers, creating a separate set of Flyway migrations, and configuring the application to connect to a PostgreSQL database. This will provide a more robust and scalable database option for production environments.

*As a developer, I want to use PostgreSQL in production so that I can leverage its advanced features and performance.*

## 3. Tasks

- [ ] Add PostgreSQL JDBC driver dependency.
- [ ] Create a new set of Flyway migrations for PostgreSQL.
- [ ] Configure the application to connect to a PostgreSQL database.
- [ ] Update the repository pattern to be compatible with both SQLite and PostgreSQL.

## 4. Technical Notes & Considerations

- The application should be able to switch between SQLite and PostgreSQL using a configuration flag.
- The Flyway migrations for PostgreSQL will be located in a separate directory (e.g., `src/main/resources/db/psql/migrations`).

## 5. Open Questions

- How will the application handle database-specific features?
