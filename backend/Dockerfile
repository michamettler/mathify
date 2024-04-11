# Use Ubuntu as base image
FROM ubuntu:22.04

# Set environment variables for Java and Gradle
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin
ENV GRADLE_HOME=/opt/gradle
ENV PATH=$PATH:$GRADLE_HOME/bin

# Specify the port the applications listens on
EXPOSE 8080 8443

# Install necessary packages
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk git wget unzip && \
    apt-get clean

# Set up Gradle
WORKDIR /opt
RUN wget https://services.gradle.org/distributions/gradle-8.7-bin.zip && \
    unzip gradle-8.7-bin.zip && \
    rm gradle-8.7-bin.zip && \
    mv gradle-8.7 gradle

# Copy repository
COPY . /opt/repository

# Copy certificates
COPY NO_CHECKIN /etc/ssl

# Set working directory
WORKDIR /opt/repository

# Build the application using Gradle
RUN gradle build
CMD ["gradle", "run"]