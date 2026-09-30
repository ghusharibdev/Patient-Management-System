FROM maven:3.9.16-eclipse-temurin-25 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp -DskipTests clean package

FROM eclipse-temurin:25-jre AS runner

WORKDIR /app

RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
USER appuser

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 4004 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
