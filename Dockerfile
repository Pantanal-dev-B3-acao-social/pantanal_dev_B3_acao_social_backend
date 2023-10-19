#FROM maven:3.9.4 AS mavenImg
FROM maven:3.9.4-eclipse-temurin-17 AS mavenImg
WORKDIR /usr/src/app
COPY . /usr/src/app

# Copie o arquivo pom.xml e os arquivos fonte do projeto
COPY pom.xml .
COPY src src
RUN mvn clean install
# compilar com a versão 3.11.0 do maven-compiler-plugin
#RUN mvn org.apache.maven.plugins:maven-compiler-plugin:3.11.0:compile
# Compile and package the application to an executable JAR
RUN mvn package

#FROM eclipse-temurin:17-jre-alpine
FROM maven:3.9.4-eclipse-temurin-17-alpine
# Copia o arquivo JAR do seu projeto para dentro do container
COPY --from=mavenImg /usr/src/app/acao_social-${app_version}-SNAPSHOT.jar /usr/src/app/acao_social-${app_version}-SNAPSHOT.jar

# Define o diretório de trabalho
WORKDIR /usr/src/app

EXPOSE 3001
ENTRYPOINT ["java","-jar","acao_social-${app_version}-SNAPSHOT.jar"]
