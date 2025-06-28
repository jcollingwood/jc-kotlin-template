# ---- Runtime Stage ----
FROM ubuntu:noble
RUN apt-get update && apt-get install -y \
	openjdk-21-jdk \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY server/build/libs/server-all.jar /app/server.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar", "/app/server.jar"]
