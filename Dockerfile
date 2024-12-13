FROM eclipse-temurin:23 AS builder

WORKDIR /app

COPY . .

RUN ./mvnw install -Dmaven.test.skip=true

FROM eclipse-temurin:23

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

HEALTHCHECK --interval=60s --start-period=120s --retries=3 \
   CMD curl -s -f http://localhost:${SERVER_PORT}/status || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar -Dserver.port=${PORT}
