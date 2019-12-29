#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
#MAINTAINER Ram T
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /app
#COPY --from=MAVEN_BUILD /build/target/uaa-0.0.1.jar /app/
COPY target/uaa-0.0.1.jar /app/
ENTRYPOINT ["java", "-jar", "uaa-0.0.1.jar"]
