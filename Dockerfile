#version: '3.8'
#services:
#  postgres:
#    image: postgres:15
#    environment:
#      POSTGRES_DB: AccountManagement
#      POSTGRES_USER: postgres
#      POSTGRES_PASSWORD: 12345
#    ports:
#      - "5432:5432"
#    volumes:
#      - postgres_data:/var/lib/postgresql/data
#    networks:
#      - spring-net
#
#  app:
#    build:
#      context: .
#      dockerfile: src/main/Dockerfile
#    ports:
#      - "8080:8080"
#    environment:
#      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/AccountManagement
#      SPRING_DATASOURCE_USERNAME: postgres
#      SPRING_DATASOURCE_PASSWORD: 12345
#    depends_on:
#      - postgres
#    networks:
#      - spring-net
#
#networks:
#  spring-net:
#
#volumes:
#  postgres_data:

FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM amazoncorretto:21.0.4

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]