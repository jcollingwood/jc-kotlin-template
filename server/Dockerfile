# ---- Build Stage ----
# FROM gradle:8.5-jdk17 AS build
# note: alpine base images have issues with gradle-node functionality
FROM gradle:jdk21-corretto AS build
WORKDIR /app
COPY .. /app
RUN ./gradlew server:shadowJar --no-daemon

# ---- Runtime Stage ----
FROM amazoncorretto:21-alpine3.21-jdk
WORKDIR /app
COPY --from=build /app/server/build/libs/server-all.jar /app/server.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar", "/app/server.jar"]
