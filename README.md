# Spring Security OAuth Hands-On
OIDC Login + OAuth2 Resource Server (Multiple Issuers)

This service demonstrates a Spring Boot 3 + Spring Security 6 backend that supports:
- OIDC login via external IdPs (Google, GitHub)
- OAuth2 Resource Server validating JWTs from multiple issuers
- Health checks via Spring Boot Actuator

## Requirements
- Java 21+
- Maven 3.9+
- Docker (for Redis and running the app container)
- Google OAuth Client
- GitHub OAuth App

## OAuth Provider Setup
### Google
1. Create a Google OAuth client.
2. Authorized Redirect URI:
   - `http://localhost:8081/security-sys/login/oauth2/code/google`
3. Save `CLIENT_ID` and `CLIENT_SECRET`.

### GitHub
1. Create a GitHub OAuth App.
2. Authorization callback URL:
   - `http://localhost:8081/security-sys/login/oauth2/code/github`
3. Save `CLIENT_ID` and `CLIENT_SECRET`.

## Scalability (Add More Logins / Issuers)
- Add another provider by adding a new `spring.security.oauth2.client.registration.<provider>` block and matching `spring.security.oauth2.client.provider.<provider>` block in `application.yaml`.
- Add more JWT issuers by listing them under `spring.security.oauth2.resource-server.trusted-issuers`.

## Configuration
Set these environment variables (see `.env` for placeholders):
- `MONGODB_URI`
- `MONGODB_DATABASE`
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`
- `GITHUB_CLIENT_ID`
- `GITHUB_CLIENT_SECRET`

## Run With Docker Compose
1. Update `.env` with real values.
2. Start services:
   ```bash
   docker compose up --build
   ```

Notes:
- Redis uses the `redis/redis-stack:latest` image (from Docker Hub).
- The app runs at `http://localhost:8081/security-sys`.

## Run With Docker (Image Only)
1. Build the image:
   ```bash
   docker build -t spring-security-oauth-hands-on:latest .
   ```
2. Run the container with your `.env`:
   ```bash
   docker run --env-file .env -p 8081:8081 spring-security-oauth-hands-on:latest
   ```

## Run Locally (No Docker for App)
1. Start Redis separately:
   ```bash
   docker run --name redis-stack -p 6379:6379 redis/redis-stack:latest
   ```
2. Add .env file in you run configuration environment variables.
3. Run:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints
- `/web/id-token`
- `/web/access-token`
- `/web/current-client`
- `/api/id-token`
- Actuator health: `/actuator/health/liveness`, `/actuator/health/readiness`
