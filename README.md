# User API - Desafio Backend

## Sumário

1. [Introdução](#introdução)
2. [Montando o Docker do Banco](#montando-o-docker-do-banco)
3. [Tecnologias Utilizadas](#tecnologias-utilizadas)
4. [Funcionalidades](#funcionalidades)
5. [Instalação](#instalação)
6. [Executando o Projeto](#executando-o-projeto)
7. [Testes](#testes)
8. [Endpoints Principais](#endpoints-principais)
   - [Autenticação](#autenticação)
   - [Usuários](#usuários)

## Introdução

Este projeto foi desenvolvido como parte de um teste técnico, utilizando os padrões de projeto Restful para a criação de APIs e JWT para garantir a autenticação e autorização segura dos usuários. A camada de persistência dos dados é implementada com o banco de dados relacional MySQL, executado em um contêiner Docker.

## Montando o Docker do Banco

1. Baixe a imagem do MySQL:
   ```sh
   docker pull mysql
   ```

2. Execute o contêiner do MySQL:
   ```sh
   docker run -p 3306:3306 --name nome-do-banco -e MYSQL_ROOT_PASSWORD=senhaRoot -d mysql
   ```

## Tecnologias Utilizadas

- Java ☕: Linguagem de programação utilizada para o desenvolvimento do projeto.
- Spring Boot 🌱: Framework para criação de aplicações Java com configuração mínima.
- Spring Security 🔒: Framework para autenticação e autorização.
- Spring Data JPA 🔍: Abstração para trabalhar com JPA e bancos de dados relacionais.
- MySQL 🐬: Sistema de gerenciamento de banco de dados relacional.
- Docker 🐳: Plataforma para criação e gerenciamento de contêineres.
- Swagger 📚: Ferramenta para documentar APIs RESTful.
- JUnit 🧪: Framework de testes em Java.

## Funcionalidades

- **CRUD de Usuários**: Criação, leitura, atualização e remoção de usuários.
- **Autenticação e Autorização**: Utilização de JWT para garantir a segurança do sistema.
- **Documentação de API**: Documentação automática utilizando Swagger.
- **Validação de Dados**: Utilização de Bean Validation para validação de dados.

## Instalação

1. Clone o repositório:
   ```sh
   git clone https://github.com/ksguimaraes/user-api.git
   ```
2. Navegue até o diretório do projeto:
   ```sh
   cd microservice.user
   ```
3. Configure o banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nome-do-banco
   spring.datasource.username=usuario
   spring.datasource.password=senha
   jwt.secret=sua_chave_secreta
   ```

## Executando o Projeto

1. Inicie o servidor:
   ```sh
   mvn spring-boot:run
   ```
2. Acesse a documentação da API no navegador:
   ```
   http://localhost:8080/swagger-ui.html
   ```
![image](https://github.com/ksguimaraes/microservice.user/assets/39937365/da86d95f-c0b3-40bc-b1ae-ba99d582ed4b)

## Testes

Para executar os testes, utilize o comando:

```sh
mvn test
```

## Endpoints Principais

### Autenticação

- **POST /login**: Realiza a autenticação do usuário e retorna um token JWT.

### Usuários

- **POST /users**: Cria um novo usuário.
- **GET /users/:id**: Retorna os detalhes de um usuário específico.
- **PUT /users/:id**: Atualiza os dados de um usuário.
- **DELETE /users/:id**: Remove um usuário.
