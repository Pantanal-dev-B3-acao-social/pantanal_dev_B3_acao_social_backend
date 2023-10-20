FROM maven:3.9.4-eclipse-temurin-17 AS mavenImg
WORKDIR /app
COPY . /app

# Copie o arquivo pom.xml e os arquivos fonte do projeto
COPY pom.xml .
COPY src src
#RUN mvn clean
#RUN mvn dependency:resolve
 # RUN mvn dependency:resolve-plugins
# Compile and package the application to an executable JAR
#RUN mvn clean package -DskipTests
ADD /target/acao_social-0.0.1-SNAPSHOT.jar /app/acao_social-0.0.1-SNAPSHOT.jar

FROM maven:3.9.4-eclipse-temurin-17
ENV app_version=0.0.1-SNAPSHOT
ENV app_port=3001
# Copia o arquivo JAR do seu projeto para dentro do container
COPY --from=mavenImg /app/acao_social-0.0.1-SNAPSHOT.jar /app/acao_social-0.0.1-SNAPSHOT.jar

# Define o diretório de trabalho
WORKDIR /app
EXPOSE $app_port

ENTRYPOINT ["java","-jar","acao_social-0.0.1-SNAPSHOT.jar"]
