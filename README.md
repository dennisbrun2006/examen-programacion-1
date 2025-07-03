# Examen Programación 1

Aplicación web desarrollada con Spring Boot, PostgreSQL, Spring Security, Thymeleaf y Bootstrap.

## Requisitos

- Java 17+
- PostgreSQL
- Maven

## Configuración

1. Crear una base de datos llamada `mybd` en PostgreSQL.
2. Configurar usuario y contraseña de PostgreSQL en `src/main/resources/application.properties`:

    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/mybd
    spring.datasource.username=TU_USUARIO
    spring.datasource.password=TU_PASSWORD
    ```

3. Ejecutar el proyecto:

    ```
    ./mvnw spring-boot:run
    ```

4. Ingresar a [http://localhost:8080/login](http://localhost:8080/login)

5. Registrate



## Observaciones

- Incluye CRUD para usuarios, categorías y productos, con paginación, filtros y seguridad.
