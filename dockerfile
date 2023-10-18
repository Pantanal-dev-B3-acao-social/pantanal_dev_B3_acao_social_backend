FROM openjdk:17-jre-slim
WORKDIR /app
#COPY target/docker-spring-boot-postgres-1.0.0.jar .
COPY target/your-spring-boot-app.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-spring-boot-postgres-1.0.0.jar"]
