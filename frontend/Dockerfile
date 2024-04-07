# Use a base image with both JDK and Node.js installed. Ubuntu as the base to install everything:
FROM ubuntu:22.04

# Expose the application's port
EXPOSE 4200

# Install JDK (adjust JDK version as needed)
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk

# Install Node.js
RUN apt-get install -y curl && \
    curl -sL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs

# Verify installations
RUN java -version
RUN node -v
RUN npm -v

WORKDIR /opt

RUN npm install
RUN ./gradlew buildAngular

# Copy repository
COPY . /opt/repository
# Set working directory
WORKDIR /opt/repository

# Use the Gradle task to serve the Angular application
CMD ["./gradlew", "serveAngular"]
