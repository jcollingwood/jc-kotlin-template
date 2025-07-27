# Feature: DevOps and Deployment Setup

- **Status:** Done
- **Author:** joel
- **Date:** 2025-07-23

## 1. Goal

To establish a CI/CD pipeline and a containerized deployment strategy for the application.

## 2. Description

This feature focused on automating the build, test, and packaging process, and simplifying deployments. A GitHub Actions workflow was created to run tests and build a Docker image on every pull request and merge to the main branch. A `Dockerfile` was written to containerize the web server, and `docker-compose.yml` files were created to manage the local development environment. Finally, a set of shell scripts was developed to facilitate manual deployments of the Dockerized application to a server.

*As a developer, I want an automated CI/CD pipeline and a containerized environment so that I can reliably build, test, and deploy the application.*

## 3. Tasks

- [x] Create a GitHub Actions workflow for PR checks (testing and building).
- [x] Create a GitHub Actions workflow to build and push a Docker image on merge.
- [x] Write a `Dockerfile` to containerize the Kotlin web server.
- [x] Create `docker-compose.yml` files for local and production-like environments.
- [x] Develop shell scripts to manage deployments (e.g., start, stop, restart).

## 4. Technical Notes & Considerations

- The GitHub Actions workflows are defined in the `.github/workflows` directory.
- The `docker-compose.yml` files manage the web server and its dependencies (e.g., a database).
- The deployment scripts are located in the `scripts/deployment` directory.

## 5. Open Questions

- None
