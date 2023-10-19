

FROM maven:3.9.4-eclipse-temurin-17 AS mavenImg
WORKDIR /usr/src/app
COPY . /usr/src/app

# Copie o arquivo pom.xml e os arquivos fonte do projeto
COPY pom.xml .
COPY src src
RUN mvn clean
RUN mvn dependency:resolve
RUN mvn dependency:resolve-plugins
# Compile and package the application to an executable JAR
#RUN mvn package -D skip.test
RUN mvn clean package -DskipTests

#FROM eclipse-temurin:17-jre-alpine
FROM maven:3.9.4-eclipse-temurin-17
ENV app_version=0.0.1-SNAPSHOT
ENV app_port=3001
# Copia o arquivo JAR do seu projeto para dentro do container
COPY --from=mavenImg /usr/src/app/acao_social-${app_version}.jar /usr/src/app/acao_social-${app_version}.jar

# Define o diretório de trabalho
WORKDIR /usr/src/app

EXPOSE ${app_port}
ENTRYPOINT ["java","-jar","acao_social-${app_version}.jar"]
