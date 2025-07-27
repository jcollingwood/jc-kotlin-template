# Feature: Container Registry Integration

- **Status:** Done
- **Author:** joel
- **Date:** 2025-07-24

## 1. Goal

To store the application's versioned Docker image in the GitHub Container Registry (GHCR) and use that remote image for deployments.

## 2. Description

This feature established a workflow for publishing the application's Docker image to a remote registry. The CI/CD pipeline was configured to push the built image to GHCR upon a successful merge to the main branch. A corresponding `docker-compose.yml` file was created to demonstrate how a deployment environment can pull this specific image version from the registry to run the application, decoupling the build environment from the deployment environment.

*As a developer, I want to publish our application as a container image so that I can easily version, distribute, and deploy it in a consistent manner.*

## 3. Tasks

- [x] Configure GitHub Actions to authenticate with the GitHub Container Registry.
- [x] Update the build workflow to tag and push the Docker image to GHCR.
- [x] Create a `docker-compose.yml` file that references the image from GHCR.
- [x] Document the process for pulling and running the containerized application.

## 4. Technical Notes & Considerations

- The GitHub Actions workflow uses a `GITHUB_TOKEN` to authenticate with GHCR.
- The `docker-compose.yml` file specifies the image using the `ghcr.io/OWNER/IMAGE_NAME:TAG` format.

## 5. Open Questions

- None
