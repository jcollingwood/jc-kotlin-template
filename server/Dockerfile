# ---- Runtime Stage ----
# note: alpine base images have issues with gradle-node functionality
FROM ubuntu:noble
# note: to get linux/arm/v7 compatibility, building jdk image from scratch
RUN apt-get update && apt-get install -y \
	openjdk-21-jdk \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY server/build/libs/server-all.jar /app/server.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar", "/app/server.jar"]
