# Feature: Google OAuth Integration

- **Status:** Done
- **Author:** joel
- **Date:** 2025-07-22

## 1. Goal

To allow users to authenticate with the application using their Google account.

## 2. Description

This feature implements a complete authentication flow using Google as an OAuth 2.0 provider. It allows a user to click a "Login with Google" button, be redirected to Google for authentication, and then be returned to the application. Upon successful authentication, a user session is created, granting them access to the application.

*As a user, I want to log in with my Google account so that I don't have to create and remember a separate password.*

## 3. Tasks

- [x] Configure the Ktor server with the OAuth extension.
- [x] Set up a Google Cloud project with the necessary OAuth 2.0 credentials (client ID and secret).
- [x] Implement the `/login` and `/callback` routes to handle the authentication flow.
- [x] Create a user session upon successful authentication.
- [x] Add a login button to the UI.

## 4. Technical Notes & Considerations

- The implementation uses the `ktor-server-auth` library.
- Securely stores the Google client secret as an environment variable, not in source code.

## 5. Open Questions

- None
