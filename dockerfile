# Stage 1: Build stage
FROM adoptopenjdk:11 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project directory into the container
COPY . /app

# Compile the Java code
RUN find /app/src -name "*.java" > sources.txt \
    && javac -d /app/bin -cp "/app/lib/*" @sources.txt

#==========================================================

# Stage 2: Create JAR stage
FROM adoptopenjdk:11 as package

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java classes from the build stage
COPY --from=builder /app/bin /app/bin

# Create the JAR file
RUN jar cf api.jar -C /app/bin .

#==========================================================

# Stage 3: Use a Java runtime as the base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the UI files and the external libraries into the container
COPY ui /app/ui
COPY lib /app/lib

# Copy the jar file from the package stage
COPY --from=package /app /app

# Expose port 9000 for the HTTP Server
EXPOSE 9000

# Set the entry point to execute the Java application
ENTRYPOINT ["java", "-cp", "api.jar:lib/*", "server.Main"]
