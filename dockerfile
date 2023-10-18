FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the Maven wrapper script and the POM file

COPY mvnw .
#COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Set the JAVA_HOME to the JRE path in the container
ENV JAVA_HOME=/usr/lib/jvm/default-jvm/jre

# Download the project dependencies
RUN ./mvnw dependency:go-offline -B
# Copy the entire project directory into the container
COPY . .
# Build the Spring Boot application and create a new JAR
# criar base de dados "postgres" e "postgres_testing"
RUN ./mvnw package
#ENV SPRING_PROFILES_ACTIVE=production
EXPOSE 3001
ENTRYPOINT ["java","-jar","acao_social-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "acao_social-0.0.1-SNAPSHOT.jar"]
