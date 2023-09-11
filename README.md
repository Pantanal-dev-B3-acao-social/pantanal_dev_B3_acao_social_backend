# pantanal_dev_B3_acao_social

# Descrição
O projeto consiste em
Microserviço de gestão de ação social

# Processo de Deploy (ambiente de produção)
- docker-compose up

# Processo de configuração do ambiente de desenvolvimento
- docker-compose up

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
  - Root URL: http://localhost:3001/
  - Home URL: http://localhost:3001/
  - Valid redirect URIs: http://localhost:3001/*
  - Admin URL: http://localhost:3001/
- request para autenticação, e retorna access_token
  - grant_type: password
  - client_id: client-id-backend-1
  - username: funcionario1
  - password: 123
  - client_secret: XHlNeEfmueMrWomAff3SNAccZNGbrOxX


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

# Gerencia e configuração
- criaçaõ de branch
@master
pois esta sendo criada a partir da branch master
 e assim por diantes

@master/54687_feijao

se quiser criar uma branch a partida da feijao fica assim


@master/@54687_feijao/89743_pipoca
```
- gitflow
- commit semantico
