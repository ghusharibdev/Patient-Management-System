# Patient-Management-System

A patient management system built as independent Spring Boot services that talk to each other
over REST, gRPC and Kafka. Every service uses the same controller -> service -> repository ->
mapper layering, manual getters/setters, Springdoc/OpenAPI annotations and `.http` request files.

## Services

| Service | Port | Transport | Responsibility |
| --- | --- | --- | --- |
| patient-service | 4000 | REST | Patients CRUD. Publishes `patient-events`. |
| appointment-service | 4001 | REST | Books appointments. Validates the patient via patient-service and publishes `appointment-events`. |
| auth-service | 4002 | REST | Registers/logs in users and issues JWTs. |
| notification-service | 4003 | REST + Kafka consumer | Stores every domain event it receives. |
| billing-service | 4004 REST / 9090 gRPC | gRPC + REST + Kafka | Creates billing accounts (via gRPC or automatically from `patient-events`) and publishes `billing-events`. |

Swagger UI is available on the REST services, e.g. <http://localhost:4000/swagger-ui.html>.

## Event flow

```
patient-service --patient-events--> billing-service --billing-events--+
                \                                                     \
                 \                                                     v
appointment-service --appointment-events--> notification-service <-- (all events)
```

Events are plain JSON strings; each service keeps its own local copy of the payload record.

| Topic | Published by | Consumed by |
| --- | --- | --- |
| `patient-events` | patient-service | billing-service, notification-service |
| `appointment-events` | appointment-service | notification-service |
| `billing-events` | billing-service | notification-service |

## Prerequisites

- Docker + Docker Compose

For running the services outside Docker you also need JDK 25. Every service ships a Maven
wrapper pinned to the same Maven version, so use `./mvnw` rather than a system Maven.

## Running everything with Docker

```bash
docker compose up -d --build
```

This builds and starts PostgreSQL, Kafka and all five services. Wait until every container
reports `healthy`:

```bash
docker compose ps
```

Then try the flow using the files under `api-requests/`:

1. create a patient on 4000
2. log in on 4002 to get a JWT
3. book an appointment for that patient on 4001
4. check the billing account created automatically by Kafka on 4004
5. check the stored notifications on 4003

Stop everything with `docker compose down`. Add `-v` as well to also drop the database and
Kafka volumes.

## Running the services outside Docker

The infrastructure can run in Docker while the services run on your machine:

```bash
docker compose up -d patient-service-db kafka

cd patient-service      && ./mvnw spring-boot:run
cd appointment-service  && ./mvnw spring-boot:run
cd auth-service         && ./mvnw spring-boot:run
cd notification-service && ./mvnw spring-boot:run
cd billing-service      && ./mvnw spring-boot:run
```

Kafka listens on two addresses, so both setups work: `localhost:9092` for host processes and
`kafka:29092` for containers.

## Notes

- All services share one PostgreSQL database and create their own tables.
- Docker Compose is managed manually (`spring.docker.compose.enabled=false` in patient-service)
  because every service shares the same Postgres/Kafka stack.
- `jwt.secret` in auth-service is a development-only value - change it before any real use.
- Seed data in `patient-service/src/main/resources/data.sql` is idempotent.
- If Kafka refuses to start against a volume created by an older configuration, reset the
  disposable volumes with `docker compose down -v`.
