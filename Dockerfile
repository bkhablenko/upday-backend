FROM eclipse-temurin:17.0.6_10-jdk-alpine

ARG JAR_FILE="./build/libs/upday-backend-0.1.0-SNAPSHOT.jar"

RUN apk update && \
    apk add --no-cache curl && \
    addgroup -S upday && \
    adduser -S appuser -G upday

USER appuser:upday

COPY "$JAR_FILE" /app.jar

HEALTHCHECK --interval=10s --timeout=5s --start-period=30s \
    CMD curl http://localhost:8080/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
