# API Gateway Service

A backend API Gateway that authenticates clients via JWT, enforces per-user rate limiting using Redis, proxies requests to downstream services, and logs traffic to PostgreSQL with a queryable analytics API.

## Architecture

```
Client Request
      ↓
JWT Filter — validates Bearer token
      ↓
Rate Limiter — checks Redis (max 10 requests per 60 seconds per user)
      ↓
Proxy — forwards request to downstream service (httpbin.org)
      ↓
Async Logger — writes request metadata to PostgreSQL in background
      ↓
Response returned to client
```

## Features
- JWT-based user registration and login
- Per-user rate limiting via Redis (10 requests per 60 second window)
- Request proxying to configurable downstream services
- Async request logging to PostgreSQL (non-blocking)
- Analytics API — top users by request count, slowest endpoints by average response time

## Tech Stack
- Java 17, Spring Boot 3
- Spring Security + JWT (jjwt)
- Redis (rate limiting)
- PostgreSQL (request logging)
- WebClient (downstream HTTP calls)
- Docker + Docker Compose

## Running Locally

Prerequisites: Docker, Java 17

1. Clone the repository
2. Start dependencies:
```bash
docker-compose up -d
```
3. Run the Spring Boot application

## API Endpoints

### Auth
```
POST /auth/register
Body: { "emailId": "user@example.com", "password": "yourpassword" }
Response: JWT token

POST /auth/login
Body: { "emailId": "user@example.com", "password": "yourpassword" }
Response: JWT token
```

### Proxy
```
GET /proxy/**
Header: Authorization: Bearer <token>
Forwards request to downstream service
Returns 429 if rate limit exceeded
```

### Analytics
```
GET /analytics/summary
Header: Authorization: Bearer <token>
Returns: top 5 users by request count, slowest 5 endpoints by avg response time
```
