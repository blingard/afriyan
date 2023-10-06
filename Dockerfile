FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
RUN mkdir /file
COPY uploads/ /file
ENTRYPOINT ["java","-jar","/app.jar"]