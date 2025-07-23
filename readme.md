# Kotlin Template Project

Basic kotlin project setup for fast project setup with my updated and preferred tech stack.

The idea is that this will serve as my central catch-all project sample where I can experiment with new tech, maintain
samples and references, and this project can be cloned and modified to give a fully functional starting point for a new
project requiring a backend server.

## TODOs

- [x] create new project from template
- [x] basic project structure
- [x] OAUTH integration
- [x] MPA sample
- [x] HTMX sample
- [x] Standard Components and Javascript samples
- [x] Database integration sample (SQLite)
- [ ] Database integration sample (Postgres)
- [ ] Unit tests sample
- [x] GH Actions sample
- [x] Docker sample
- [x] Container registry sample
- [x] Infrastructure sample
- [ ] CI/CD sample
- [x] Data cleanup/pruning sample
- [ ] User data and permissions sample
- [ ] Admin management sample
- [ ] Data viz sample

- [x] Dependencies updated (2025-07-23)

## Backend Server:

- [ktor](https://ktor.io/)
    - web framework for kotlin
    - leveraging http client, routing, sessions, and auth
- [sqlite](https://sqlite.org/index.html)
    - lightweight database for local storage
- [postgresql](https://www.postgresql.org/)
    - a "real" database ;)
- [flyway](https://flywaydb.org/)
    - used to manage database migrations for both sqlite and postgres
    - build into server startup to run migrations against data source

## Frontend:

- [htmx](https://htmx.org/)
    - basic htmx utility functions provided
    - future improvements may include migrating htmx utilities and common components to their own library for reuse
- [tailwindcss](https://tailwindcss.com/)
    - hooked into gradle build process to scan for tailwind classes and generate styles.css
- [html dsl for templating](https://kotlinlang.org/docs/typesafe-html-dsl.html)

## Running

To run in development mode with autoreload enabled, run with system property `-Dio.ktor.development=true`

Required environment variables:

- `JC_TEMPLATE_GOOGLE_CLIENT_ID` - google client id of OAUTH google application
- `JC_TEMPLATE_GOOGLE_CLIENT_SECRET` - google client secret of OAUTH google application

Can generate local.env file with the following command:

```bash 
sh ./scripts/generate-local-env.sh
```

Optional environment variables:

- `PRUNE_DURATION_HOURS`
    - frequency of data pruning background process (default is hourly)
- `JC_TEMPLATE_DOMAIN`
    - domain to run on, defaults to `localhost`
    - note: ensure this domain value is reflected on OAUTH configured redirect urls
- `PORT`
    - port to run on, defaults to 3333
    - note: ensure this port value is reflected on OAUTH configured redirect urls
- `JC_TEMPLATE_DB_TYPE`
    - database type to use, defaults to `sqlite`
    - valid values are `sqlite` or `postgres`
- `SQLITE_CONN_URL`
    - connection url for sqlite database
    - if not set, sqlite will default to `jdbc:sqlite:./db/template_db.db`
- `JC_TEMPLATE_PSQL_CONN_URL`
    - TODO not finished
    - connection url for sqlite or postgres database
    - if not set, will default to `jdbc:postgresql://localhost:5432/template_db`

To run the app, run the following command:

```bash
# manually set environment variables or get via script using gcp cli (generates local.env file)
./scripts/get_local_env.sh

# if you have a .env file you can load it with the following command
export $(cat local.env | xargs)

# to run server
./gradlew run
```

## Build and Run with Docker

```bash
# to build local image for server 
 docker buildx build --platform linux/amd64,linux/arm/v7 -t ghcr.io/jcollingwood/jc-kotlin-template/server:0.0.0 -f server/Dockerfile .

# to run local image with docker compose
docker compose -f docker-compose.yml -f docker-compose.local.yml up [-d]
```

