# Introduction to Hexagonal Architecture with Spring Boot and Kotlin - Avengers API

Development of an API using SpringBoot + Kotlin to register Avengers characters.

## Technologies / Frameworks / IDE

- Intellij
- SpringBoot 3.2.4
- Maven
- Kotlin 1.9.23
- SpringData JPA
- PostgresSQL
- Undertow - Tomcat alternative
- Flyway
- Java 21
- Heroku

## Creation of the project frame

- https://start.spring.io/

Add the starters:

- spring-boot-starter-web
- spring-boot-starter-validation
- spring-boot-starter-data-jpa
- spring-boot-starter-test
- spring-boot-starter-undertow (manually)
- spring-boot-starter-flyway
- Kotlin

Preferably see the pom.xml file.

## Define the API contract

Use API first approach.

- https://editor.swagger.io/

- Resource `avenger`
- GET - 200 OK
- GET {id}/detail - 200 OK or 404 Not Found
- POST - 201 Created or 400 Bad Request
- PUT {id} - 202 Accepted or 404 Not Found
- DELETE {id} - 202 Accepted or 404 Not Found

```json
{
  "id": 1,
  "nick": "spider-man",
  "person": "Peter Parker",
  "description": "super powers",
  "history": "the history"
}
```

## Architectural Design

- Application Layer (controllers, configs, exception handle, request and response dtos, bean validations)
- Domain Layer (avenger model, repository interface, service)
- Infrastructure Layer (jpa repository, avenger entity, proxy implements repository interface and uses the jpa repository to communicate with the database)
- Tests

### Application Layer

Create web.resource, request, response and its controllers.

### Domain Layer

Create Avenger class.

### Repository Layer

Create AvengerRepository interface.

### Service Layer

Create AvengerService interface, AvengerServiceImpl or simply AvengerService.

### Resources - profiles

Create application.yaml, application-dev.yaml and application-heroku.yaml. Code below.

#### Profiles

- application.yaml

```yaml
spring:
  application:
    name: avengers
  config:
    # This configuration allow use profiles as spring 2.3.x version
    # In spring 2.4.x version, has changed to:
    # spring:
    #  profiles:
    #    group:
    #      <group>: dev, auth
    use-legacy-processing: true
  profiles:
    active: dev
  jmx:
    enabled: false
  data:
    jpa:
      repositories:
        bootstrap-mode: deferred
  jpa:
    open-in-view: false
    properties:
      hibernate.jdbc.time_zone: UTC
      hibernate.generate_statistics: false
      hibernate.jdbc.batch_size: 25
      hibernate.order_inserts: true
      hibernate.order_updates: true
      hibernate.query.fail_on_pagination_over_collection_fetch: true
      hibernate.query.in_clause_parameter_padding: true
    hibernate:
      ddl-auto: none
      naming:
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
        implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
  main:
    allow-bean-definition-overriding: true
  task:
    execution:
      thread-name-prefix: avengers-task-
      pool:
        core-size: 2
        max-size: 50
        queue-capacity: 10000
    scheduling:
      thread-name-prefix: avengers-scheduling-
      pool:
        size: 2
  output:
    ansi:
      console-available: true

server:
  port: 9090
  servlet:
    session:
      cookie:
        http-only: true
    context-path: /avengers
```

- application-dev.yaml


```yaml
spring:
  profiles:
    active: dev
  jackson:
    serialization:
      indent-output: true
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://localhost:25433/${DB_NAME:avengers_db}
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:123456}
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    show-sql: true
```

- application-heroku.yaml

```yaml
spring:
  profiles:
    active: heroku
  jackson:
    serialization:
      indent-output: true
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://${HOST}/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    show-sql: false
```

### Flyway (db/migration)

At resources/db.migration, create a file following this convention:
Vyyyymmddhhmmss__create_avenger_table.sql
V year month day hour minute second __ create avenger table

```sql
create table avenger (
    id bigserial not null,
    nick varchar(36),
    person varchar(128),
    description varchar(128),
    history text,
    primary key (id)
);

alter table avenger add constraint UK_5r88eemotwgru6k0ilqb2ledh unique (nick);
```

## Docker

### Environment Config

`.env`

```sh 
DB_USER=postgres
DB_PASSWORD=123456
DB_NAME=avengers_db
```

### YAML (avenger-api-resources.yaml)

```yaml
version: '3.2'
services:
  postgres-avengers:
    image: postgres:12-alpine
    environment:
      POSTGRES_USER: ${DB_USER}
      POSTGRES_DB: ${DB_NAME}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
      ALLOW_IP_RANGE: 0.0.0.0/0
    ports:
      - "25433:5432"
    volumes:
      - pdb12:/var/lib/postgresql/data
    networks:
      - postgres-compose-network

  pgadmin-avenger:
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PGADMIN_DEFAULT_EMAIL}
      PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_DEFAULT_PASSWORD}
    ports:
      - "5556:80"
    depends_on:
      - postgres-avengers
    networks:
      - postgres-compose-network

volumes:
  pdb12:
networks:
  postgres-compose-network:
    driver: bridge
```

### Script / Commands

#### Run Docker to test

- `cd docker`
- `docker-compose -f avenger-api-resources.yaml up -d` (deploy) / `docker-compose -f avenger-api-resources.yaml down` (undeploy)

#### Alternative to run the application: Start API

`start_api.sh`
```sh
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-Xmx256m -Xms128m" -Dspring-boot.run.arguments="'--DB_USER=postgres' '--DB_PASSWORD=123456' '--DB_NAME=avengers_db'"
``` 
Run in the terminal:

`sh start_api.sh`

### Procfile - For Heroku

```text
web: java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap $JAVA_OPTS -Dserver.port=$PORT -Dspring.profiles.active=heroku -jar target/*.jar
```

## Testing with Postman

GET http://localhost:9090/avengers/v1/api/avenger
Should not return anything relevant.

GET http://localhost:9090/avengers/v1/api/avenger/1/details
Should return 404 error.

POST http://localhost:9090/avengers/v1/api/avenger
Body raw JSON:

```json
{
  "nick": "spider-man",
  "person": "Peter Parker",
  "description": "super powers",
  "history": "the history"
}
```

Should return 201 Created - status code. It will assign automatically id = 1.

GET http://localhost:9090/avengers/v1/api/avenger/1/details
Should return 200 OK - status code.

POST http://localhost:9090/avengers/v1/api/avenger
Body raw JSON:

```json
{
    "nick": "superman",
    "person": "Clark Kent"
}
```

Should return 201 Created - status code. As other fields can be null, it will work. id = 2.

POST http://localhost:9090/avengers/v1/api/avenger
Body raw JSON:

```json
{
  "nick": null,
  "person": "Thor Odinson",
  "description": "super powers",
  "history": "the history"
}
```

Should return Bad Request - status code. As null is not allowed.

But strangely if we use "nick": "null", it should return 201 Created - status code.

PUT http://localhost:9090/avengers/v1/api/avenger/2
Body raw JSON:
```json
   {
        "nick": "Superman",
        "person": "Clark Kent",
        "description": "super strength",
        "history": "From Krypton"
    }
```

Should return 200 OK - status code.

GET http://localhost:9090/avengers/v1/api/avenger/2/details
Should return 200 OK - status code.

DELETE http://localhost:9090/avengers/v1/api/avenger/2
Body none.
Should return 200 OK - status code.

## Heroku

- Create app
- Set environment variables
- Link with GitHub
- Deploy

## Create app

  Create an app at https://dashboard.heroku.com/apps
  Create a PostgresSQL database at Resources tab, Add-ons and search for PostgresSQL.
  Copy the database credentials:
  Host with port (example: host.com:5432), Database name, Username, Password

## Set environment variables

  Set environment variables at Heroku Settings, Config Vars and add the previously copied credentials.
  The DATABASE_URL probably is already set in Heroku.
  Use the same keys in this YAML file: HOST, DB_NAME, DB_USER, DB_PASSWORD

## Link with GitHub
  Grant access of the project GitHub repository to Heroku.
  Deploy the project in Heroku:
  Go to Deploy tab, Deploy method and select GitHub, set the repository URL.
  Set your deployment preferences. It is possible to create pipeline manually.

## Deploy

  Deploy the project. It will provide a URL for the app and a link to the logs.
  Open Postman or any other tool and paste the URL in the request URL field and test the app.
  The app should work as expected.

