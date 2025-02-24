# jc kotlin template

Basic kotlin project setup for fast project setup with my updated and preferred tech stack.

## Backend Server:

- [ktor](https://ktor.io/)
    - web framework for kotlin
    - leveraging http client, routing, sessions, and auth

## Frontend:

- [htmx](https://htmx.org/)
    - basic htmx utility functions provided
    - future improvements may include migrating htmx utilities and common components to their own library for reuse
- [tailwindcss](https://tailwindcss.com/)
    - hooked into gradle build process to scan for tailwind classes and generate styles.css
- [html dsl for templating](https://kotlinlang.org/docs/typesafe-html-dsl.html)

## Running

To run in development mode with autoreload enabled, run with system property `-Dio.ktor.development=true`

