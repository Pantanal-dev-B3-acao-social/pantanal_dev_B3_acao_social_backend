# pantanal_dev_B3_acao_social

# Descrição
- O projeto consiste em uma aplicação monolítica de gestão de ação social.
- Empresa (Company)
- ONG
- Ação Social (Social Action) é o projeto de uma ONG
- Company pode fazer diversos investimentos em uma Social Action
- Sessão (session) A Social Action pode ocorrer em um ou muitas sessões
- voluntario (vonluntary) pode se candidatar ou ser convidado para ser da Staff
- Staff são os voluntarios membros da equipe organizadora de uma ação social
- o voluntario pode ir em qualquer session para trabalhar
- a presença do voluntario na sessão deve ser registrada via CPF ou QR code

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
  - [LOG DE RASTREABILIDADE PARA AUDITORIA]
- postgres
- docker
- docker-compose
- keyclock
- spring-data-envers (revisao de mudança do registro)

# Perfis
- Admininstrador
- Gerente da empresa
- [futuro] funcionario da empresa
- [futuro] Gerente pela ONG
- [futuro] funcionario da ONG
- [futuro] doardor PF

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
SPRING_PROFILES_ACTIVE=dev

# Organização de diretorios
- os diretorios estão organizados em modulos 
- apesar de não ser uma arquitetura modular 
- a equipe optou por esta organização para facilitar na visualizações de arquivos durante o desenvolvimento

# Arquitetutra do software
- Arquitetura Monolítica, apesar de ter SSO
- A arquitetura do software é inspirada na arquitetura baseada em serviço, pois o fluxo das requisições são orientadas por "services" não "domain"
- a maior parte das regras de negocio estão contidas na camada de "service"
- podem existir algumas regras de negocio voltada para dados em outras cadamadas, principalmente validadores
- como na camada de DTO, Entity e Migration
- 
### SSO (Single sign-on) Autenticação única
- optamos por não implementar os serviços de autenticação e autorização
- optemos por usar uma ferramenta de SSO, no caso o Keyclock
- em nossa arquitetura, tercerizamos para o Keyclock gerencia tudo do usuário
- e o backend spring boot somente recebe o ID do user suas autorizações a partir do token da request
- que ja esta integrado com o spring security e validando a autenticação e autorização antes de executar as ações do controller

### responsabilidade de cada camada de acordo com a arquitetura proposta
- controller
  - a camada de controller tem a responsabilidade de receber e retornar requisições HTTP rest
- service
- dto
- entity
- config
  - security
  - annotaion
  - postgres
- enum
  - para evitar valores magicos e constantes soltas, vamos usar enum para estrurar as constantes
  - serão utilizadas principalmente para status, tipo, categorias, e outras informação que precisam aplcar regra de negocio em condições fixas
- repository
  - É uma camada intermediária entre a aplicação e a fonte de dados. Ele fornece métodos para acessar e manipular os dados, como criar, ler, atualizar e excluir (CRUD). O Repository abstrai a complexidade do acesso aos dados subjacentes e fornece uma interface consistente para a aplicação.
- db/migration/dataDefinitionProduction

# Configurações para o Keyclock
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
  - Credentials: client_secret: gerar secret e salvar no application.yml e application-dev.yml
- Cadastrar role
  - SOCIAL_ACTION_CREATE
  - SOCIAL_ACTION_GET_ALL
  - SOCIAL_ACTION_GET_ONE
  - SOCIAL_ACTION_UPDATE
  - SOCIAL_ACTION_DELETE
- Cadastrar usuario
  - username: funcionario1
  - password: 123
  - depois de salvar, na aba "credentials" adicionar uma senha
  - atribuir cargos para o usuario (role mapping)
- request para autenticação, e retorna access_token
  - grant_type: password
  - client_id: client-id-backend-1
  - username: funcionario1
  - password: 123
 
# Banco de ddos
```bash
# entra dentro do container docker postgres  keycloack_postgres_db
$ docker exec -it postgres_acao_social bash
# entre na databse
root@24de07c13cb3:/# PGPASSWORD=dev_password psql -U dev_user -d keycloack_postgres_db
```
- gerar dump do database do keyclock:
```bash
# gera dump
$ sudo docker exec -u postgres postgres_acao_social pg_dump -U dev_user -d keycloack_postgres_db -f /tmp/backup_keycloak.sql
# copia o backup de dentro do docker para o a maquina host 
$ sudo docker cp postgres_acao_social:/tmp/backup_keycloak.sql /home/kaio/Documentos/ufms/pantanal_dev/projeto/acao_social/db/
```
- Migration
  - sempre que criar uma nova tabela, lembre de criar junto sua tabela de auditoria, com o prefixo "z_aud_"
- Seed
  - as seed são executada automaticamente no ambiente de "development"
  - observação: em ambiente de "development" a estrutura do banco de dados esta sendo gerado a partir das migration
- https://github.com/DiUS/java-faker
- A variavel de ambiente executa o arquivo PostgresDatabaseInitialization (spring.profiles.active: dbinit)

# Auditoria
- Revisão de mudanças dos registros
  - cada vez que um registro é criado ou alterado é criado uma revisao
  - spring-data-envers
  - createdDate, createdBy, lastModifiedDate, lastModifiedBy, deletedDate, deletedBy
- Auditoria de ações do usuario logado
  - https://medium.com/@helder.versatti/implementando-correlation-id-em-uma-aplica%C3%A7%C3%A3o-spring-c9c3a92c67e5
  - cada request feita, é criada um registro em formato json no STDOUT, com ID unico para cada request, e quem o usuario ID que fez esta request
  
# Cargos e permissoes (Roles and Permission)
- existem algumas tabelas no bd utilizadas para determinar o relacionamento ManyToMany, como por exemplo, doação, voluntario e presente,
  - por exemplo "voluntario", deve ser uma tabela de junção/pivô para mapear que esta pessoa se voluntariou para participar de determinada ação social. Desta forma "voluntario" nao pode ser um cargo, pois quando uma pessoa se voluntaria é somente e exclusivamente para aquela ação social, e não automaticamente para todas.
- mas em Cargos e Permissoes do Keyclock diz respeito das capacidades que user logado tem de executar ou não determinada ação, como por exemplo o cargo funcionario_gerente_nivel_1 possui todas as permissões para criar, deletar, buscar e atualizar uma determinada ação social ou dados da empresa.
Neste cenário, quando o usuário recebe um cargo, ele tem o mesmo cargo em todas as partes do sistema, independente se ele for voluntario em uma ação social e tambem for gerente da empresa
- é de responsabilidade do Keyclock com redirecionamento de autenticação, recuperação de senha, atualizar cadastro do usuario, delegar cargos e permissões para usuario 

# Melhorias futuras
- implementar integração do Spring Boot com Redis
  - armazenar detalhes do usuario no Redis

# Testes
- estamos usando como base principal os testes de integração, que apesar de mais custosos para implementar
- agregam uma boa cobertura de testes, desde a funcionalidade em si estar funcionando e sua respectiva regra de negocio
- até integração com outros serviços
  - não estamos mockando o banco de dados e nem o SSO
  - cada caso de teste usa realmente o keyclock para autenticar e autorizar
  - verificando se o usuario esta autenticado para executar a action do controller, caso seja necessário 
  - verificando se o usuario tem a permissão para executar a action do controller, caso seja necessário
  - o banco de dados é realmente o postgres, para que garanta que restrições seja as mesmas do ambiente de produção
  - desta forma garantimos que os erros de consistencia sejam validados
  - todo caso de teste esta em uma transaction que faz rollback apos terminar  