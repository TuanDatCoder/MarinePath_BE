## Use an official OpenJDK runtime as a parent image
#FROM eclipse-temurin:21-jdk
#
## Set the working directory in the container
#WORKDIR /usr/src/
#
## Copy the jar file from the target directory to the working directory in the container
#COPY target/MarinePath_BE-0.0.1-SNAPSHOT.jar MarinePath_BE-0.0.1-SNAPSHOT.jar
#
## Expose the port the application runs on
#EXPOSE 8080
#
## Run the jar file
#ENTRYPOINT ["java", "-jar", "MarinePath_BE-0.0.1-SNAPSHOT.jar"]


# Giai đoạn build
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Giai đoạn runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /target/MarinePath_BE-0.0.1-SNAPSHOT.jar MarinePath_BE-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "MarinePath_BE-0.0.1-SNAPSHOT.jar"]