# --- Stage 1: Build Stage ---
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build JAR
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime Stage ---
FROM eclipse-temurin:25-jre AS runner

WORKDIR /app

# Run as non-root user for container security
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
USER appuser

# Copy built artifact using absolute path and wildcard
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]