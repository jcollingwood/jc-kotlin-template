# ---- Build Stage ----
# note: alpine base images have issues with gradle-node functionality
# FROM gradle:jdk21-corretto AS build

# note: to get linux/arm/v7 compatibility, building jdk image from scratch
FROM ubuntu:noble AS build
RUN apt-get update && apt-get install -y \
	openjdk-21-jdk libatomic1 \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY .. /app
RUN ./gradlew server:shadowJar --no-daemon

# ---- Runtime Stage ----
# FROM amazoncorretto:21-alpine3.21-jdk

# note: to get linux/arm/v7 compatibility, building jdk image from scratch
FROM ubuntu:noble
RUN apt-get update && apt-get install -y \
	openjdk-21-jdk \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /app/server/build/libs/server-all.jar /app/server.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar", "/app/server.jar"]
