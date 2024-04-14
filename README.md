# Bank-service

The main backend service for HackNu 2024

[![Java v21][shield-java]](https://openjdk.java.net/projects/jdk21/)
[![Spring Boot v3.5.1][shield-spring-boot]](https://spring.io/projects/spring-boot)

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Building](#building)
- [Running](#running)
- [Author](#author)

## Features

* Managing categories
* Managing banks
* Managing types of cards for each bank
* Managing users
* Managing users' cards
* Managing cashback offers

## Requirements

The list of tools required to build and run the project:

* Open JDK 21
* PostgreSQL (database)
* Docker
* Docker-compose

## Building

In order to build project use:

```bash
./gradlew clean build
```

## Running

To launch the database:

- Make sure that you have `docker` and `docker-compose` installed
- Save this script into `docker-compose.yml` file:

```yml
version: "3.9"
services:
  postgres:
    image: postgres:15.1
    container_name: hacknu2024
    environment:
      POSTGRES_DB: "hacknu_db"
      POSTGRES_USER: "postgres"
      POSTGRES_PASSWORD: "postgres"
    ports:
      - 5432:5432
```

- Execute following command in terminal

```bash
docker-compose -f docker-compose.yml up -d
```

To launch the application in the terminal:

```bash
java -jar build/libs/bank-service-0.0.1-SNAPSHOT.jar
```

Application will run by default on port 8080

## Author

Copyright &copy; 2024, Durmagambetova Aniyar

[shield-java]: https://img.shields.io/badge/Java-17-blue.svg

[shield-spring-boot]: https://img.shields.io/badge/Spring_Boot-3.1.1-blue.svg

[shield-spring-cloud]: https://img.shields.io/badge/Spring_Cloud_Gateway-4.0.3-blue.svg
