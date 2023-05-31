FROM gradle:7.2-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim

EXPOSE 8081

RUN mkdir /coffeeshop

COPY --from=build /home/gradle/src/build/libs/*.jar /app/lib/

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-cp","app:/app/lib/*", "za.co.fnb.coffeeshop.CoffeeshopApplication"]