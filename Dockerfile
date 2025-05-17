FROM eclipse-temurin:21.0.7_6-jdk-alpine-3.21

ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD
ARG JAR_FILE=build/libs/*.jar

ENV DB_URL=$DB_URL
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD

COPY $JAR_FILE app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]