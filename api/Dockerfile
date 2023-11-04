FROM maven:3.8.2-eclipse-temurin-11 as builder
LABEL maintainer="frostte32"
WORKDIR /api
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

FROM eclipse-temurin:17-jre-jammy
WORKDIR /api
RUN mkdir -p "/api/storage"
ENV STORAGE_PATH "/api/storage/"
EXPOSE 8080
COPY --from=builder /api/target/*.jar /api/*.jar
ENTRYPOINT ["java", "-jar", "/api/*.jar" ]