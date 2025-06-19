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
- [ ] Database integration sample (SQLite)
- [ ] Database integration sample (Postgres)
- [ ] Unit tests sample
- [ ] GH Actions sample
- [x] Docker sample
- [ ] Infrastructure sample
- [ ] CI/CD sample
- [ ] Data viz sample

## Backend Server:

- [ktor](https://ktor.io/)
    - web framework for kotlin
    - leveraging http client, routing, sessions, and auth
- [sqlite](https://sqlite.org/index.html)
    - lightweight database for local storage
- [postgresql](https://www.postgresql.org/)
    - a "real" database ;)

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

Optional environment variables:

- `PORT`
    - port to run on, defaults to 3333
    - note: ensure this port value is reflected on OAUTH configured redirect urls

To run the app, run the following command:

```bash
./gradlew run
```

## Build and Run with Docker

```bash
# to build local image for server
docker build -t kotlin-template-server:latest -f server/Dockerfile .

# to run local image with docker compose
docker compose up [-d]
```

