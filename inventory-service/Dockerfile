#--------------
# BUILD STAGE
#--------------

FROM gradle:8-jdk-21-and-22-alpine AS builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test


#---------------
# PACKAGE STAGE
#---------------

FROM openjdk:21-jdk

RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/inventory-service.jar

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=password

COPY ./src/main/resources/db/migration /app/db/migration

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/inventory-service.jar"]