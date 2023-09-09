# pantanal_dev_B3_acao_social

# Descrição
O projeto consiste em

# Funcionalidades
- Ação social
  - criar
  - atualizar
  - buscar um
  - buscar todos
  - deletar
# Tecnologias

# Variaveis de ambiente
SPRING_PROFILES_ACTIVE=test

# Arquitetutra 
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
