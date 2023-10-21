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

# Copie o script de espera (wait-for-it.sh)
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

FROM maven:3.9.4-eclipse-temurin-17
ENV app_version=0.0.1-SNAPSHOT
ENV app_port=3001
# Copia o arquivo JAR do seu projeto para dentro do container
COPY --from=mavenImg /app/acao_social-0.0.1-SNAPSHOT.jar /app/acao_social-0.0.1-SNAPSHOT.jar

# Copie o script de espera (wait-for-it.sh) novamente
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Define o diretório de trabalho
WORKDIR /app
EXPOSE $app_port

CMD ["./wait-for-it.sh", "172.16.19.3:9090/auth/realms/realm-pantanal-dev", "--", "java", "-jar", "acao_social-0.0.1-SNAPSHOT.jar"]
