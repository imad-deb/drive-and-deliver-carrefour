FROM openjdk:21-jdk-alpine
COPY target/DriveAndDeliver-1.jar DriveAndDeliver.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/DriveAndDeliver.jar"]
