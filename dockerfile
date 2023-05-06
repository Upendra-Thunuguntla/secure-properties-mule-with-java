# Use a Java runtime as the base image
FROM openjdk:8-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file and UI folder to the container
COPY target/api.jar /app/
COPY ui /app/ui
COPY lib /app/lib
# Expose port 9000 for the HTTPServer
EXPOSE 9000

# Set the entry point to run the HTTPServer
ENTRYPOINT ["java", "-cp", "api.jar:lib/*", "server.Main"]