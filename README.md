# Semana 6 — PostgreSQL y JPA

**Periodo:** 26 de octubre - 1 de noviembre de 2026  
**Área:** Backend con Java y Spring Boot

## Descripción

Durante esta semana aprenderemos a trabajar con bases de datos relacionales utilizando PostgreSQL e integraremos su persistencia en una aplicación Java con Spring Boot mediante JPA e Hibernate.

Aplicaremos los conocimientos desarrollando una API REST para gestionar reservas de salas, desde el diseño de la base de datos hasta las operaciones CRUD.

## Objetivos de aprendizaje

- [ ] Comprender los fundamentos de SQL.
- [ ] Consultar, insertar, actualizar y eliminar datos.
- [ ] Utilizar filtros, ordenaciones, agrupaciones y `JOIN`.
- [ ] Diseñar bases de datos relacionales.
- [ ] Comprender las claves primarias y foráneas.
- [ ] Aplicar restricciones, índices y transacciones.
- [ ] Configurar PostgreSQL en un proyecto Spring Boot.
- [ ] Comprender JPA y el papel de Hibernate como implementación.
- [ ] Crear entidades y repositorios.
- [ ] Definir relaciones entre entidades.
- [ ] Implementar operaciones CRUD mediante una API REST.
- [ ] Separar responsabilidades utilizando Controller, Service y Repository.
- [ ] Comprobar la persistencia de los datos y gestionar errores.

## Contenido de la semana

### 1. SQL y PostgreSQL

- Fundamentos de las bases de datos relacionales.
- Consultas con `SELECT`, `WHERE` y `ORDER BY`.
- Agrupaciones y funciones de agregación.
- Relaciones entre tablas mediante `JOIN`.
- Operaciones `INSERT`, `UPDATE` y `DELETE`.
- Claves primarias y foráneas.
- Restricciones, índices y transacciones.

### 2. Diseño de bases de datos

- Diseño de tablas y relaciones.
- Normalización de datos.
- Identificación de entidades y atributos.
- Integridad referencial.
- Modelado de una base de datos para gestionar reservas.

### 3. JPA e Hibernate

- Diferencias entre JPA e Hibernate.
- Configuración de la conexión entre Spring Boot y PostgreSQL.
- Mapeo de clases Java a tablas mediante `@Entity`.
- Identificadores con `@Id` y `@GeneratedValue`.
- Relaciones con `@OneToMany`, `@ManyToOne` y otras anotaciones cuando sean necesarias.
- Repositorios con Spring Data JPA.
- Guardado, consulta, actualización y eliminación de entidades.

### 4. API REST con persistencia

- Integración de Controller, Service y Repository.
- Creación de endpoints CRUD.
- Persistencia de información en PostgreSQL.
- Validación de operaciones.
- Gestión de errores y respuestas HTTP.
- Pruebas manuales de los endpoints.

## Proyecto práctico: Sistema de reservas de salas

Desarrollaremos una API REST para administrar salas y sus reservas utilizando Java, Spring Boot, PostgreSQL y JPA/Hibernate.

### Modelo de datos inicial

| Entidad | Responsabilidad |
|---|---|
| `users` | Almacenar los usuarios del sistema. |
| `rooms` | Almacenar la información de las salas. |
| `reservations` | Registrar las reservas y relacionarlas con usuarios y salas. |

Las relaciones definitivas se establecerán durante el diseño de la base de datos.

### Funcionalidades previstas

- Crear, consultar, actualizar y eliminar usuarios.
- Crear, consultar, actualizar y eliminar salas.
- Gestionar las reservas de las salas.
- Consultar los datos persistidos en PostgreSQL.
- Relacionar reservas con usuarios y salas.
- Gestionar errores en las operaciones de la API.

### Arquitectura

    Cliente HTTP
        |
        v
    Controller
        |
        v
    Service
        |
        v
    Repository
        |
        v
    JPA / Hibernate
        |
        v
    PostgreSQL

## Tecnologías

- **Java:** lenguaje de programación.
- **Spring Boot:** desarrollo de la aplicación backend.
- **Spring Web:** creación de la API REST.
- **Spring Data JPA:** acceso y persistencia de datos.
- **Hibernate:** implementación de JPA y mapeo objeto-relacional.
- **PostgreSQL:** base de datos relacional.
- **SQL:** consultas y manipulación de datos.
- **Git y GitHub:** control de versiones y publicación del proyecto.

## Recursos oficiales

- [Documentación de PostgreSQL](https://www.postgresql.org/docs/)
- [Documentación de Spring Boot](https://docs.spring.io/spring-boot/index.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/)
- [Documentación de Hibernate ORM](https://hibernate.org/orm/documentation/)

## Resultado esperado

Al finalizar la semana, tendremos una base de datos relacional diseñada para el sistema de reservas y una API REST desarrollada con Spring Boot capaz de realizar operaciones CRUD utilizando PostgreSQL y JPA/Hibernate.

El proyecto quedará documentado y publicado en GitHub, demostrando conocimientos prácticos de SQL, modelado relacional, persistencia de datos y desarrollo backend con Java.
