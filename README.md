# pantanal_dev_B3_acao_social

# Descrição
O projeto consiste em
Microserviço de gestão de ação social

# Funcionalidades
- Ação social
  - criar
  - atualizar
  - buscar um
  - buscar todos
  - deletar

# Tecnologias
- spring boot
  - spring security
  - jpa
  - flyway
  - h2
  - spring web
  - lombok
  - Junit
- postgres
- docker
- docker-compose
- keyclock

# Processo de desenvolvimento

- servidor do discord para comunicação persistente
  - compartilhamento de conteudos 
  - compartilhamento de duivadas
  - tira duvidas técnicas
  - reuniões sincronas por chamada de voz
- grupo do whatsApp para comunicação rapida e lembrentes e avisos urgentes
- Foi utilizado o github para gerencia e configuração de versionamento 
- Kanban para controle de demandas a serem desenvolvidas
- google docs para brainStorm, elaboração e documentação os requisitos


# Processo de Deploy
## Processo de execução em ambiente de produção
$ docker-compose up
$ ./mvnw spring-boot:run

## Processo de execução em ambiente de desenvolvimento
$ docker-compose up
$ SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
- variavel de ambiente para executar em ambiente de teste: "SPRING_PROFILES_ACTIVE=dev"

## Processo de execução em ambiente de teste
$ docker-compose up
$ SPRING_PROFILES_ACTIVE=test ./mvnw spring-boot:run
- variavel de ambiente para executar em ambiente de teste: "SPRING_PROFILES_ACTIVE=test"


# Gerencia e configuração com git e github
- não recomendado commitar diretamente na branch main
- proibido fazer merge para a main sem Pull Request (PR)
- PR deve ter como prefixo do nome id do card da demanda e a descrição da demanda
- criaçaõ de branch
@main
pois esta sendo criada a partir da branch main
 e assim por diantes

@main/54687_feijao

se quiser criar uma branch a partida da feijao fica assim


@main/@54687_feijao/89743_pipoca

- gitflow
- commit semantico


# Variaveis de ambiente
SPRING_PROFILES_ACTIVE=test

# Arquitetura de integração dos serviços
- microserviços se comunicando via
  Spring Cloud Feign
  Spring Cloud RestTemplate
  Spring Cloud OpenFeign
  Spring Cloud Netflix Eureka
  /REST/gRPC/graphQL/rabbitmq/WebSockets/Event Sourcing e CQRS/ SOAP/JMS (Java Message Service)/RMI (Remote Method Invocation)

# Arquitetutra do software
- controller
  - a camada de controller tem a responsabilidade de receber e retornar requisições HTTP rest
- service
- dto
- entity
- config
- enum
- repository
- db/migration/dataDefinitionProduction

# Keyclock
- criar realm: realm-pantanal-dev
- criar client:
  - client-id: client-id-backend-1
  - Client authentication: on
    - Standard flow
    - Direct access grants
    - Service accounts roles
  - Root URL: http://localhost:3001/
  - Home URL: http://localhost:3001/
  - Valid redirect URIs: http://localhost:3001/*
  - Web origins: *
  - Admin URL: http://localhost:3001/
- Cadastrar role
  - ROLE_SOCIAL_ACTION_CREATE
  - ROLE_SOCIAL_ACTION_GET_ALL
  - ROLE_SOCIAL_ACTION_GET_ONE
  - ROLE_SOCIAL_ACTION_UPDATE
  - ROLE_SOCIAL_ACTION_DELETE
- Cadastrar usuario
  - username: funcionario1
  - depois de salvar, na aba "credentials" adicionar uma senha
- request para autenticação, e retorna access_token
  - grant_type: password
  - client_id: client-id-backend-1
  - username: funcionario1
  - password: 123
  - client_secret: oXHzReNnxzgJX6VnMxhsig0sLaIheqwU

