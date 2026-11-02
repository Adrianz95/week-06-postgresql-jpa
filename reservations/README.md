# Room Reservations API

API REST desarrollada con **Java, Spring Boot, Spring Data JPA y PostgreSQL** para gestionar un sistema de reservas de salas.

El proyecto permite gestionar usuarios, salas y reservas, incluyendo validaciones, relaciones entre entidades y control de conflictos horarios.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL 16
- Maven
- Jakarta Bean Validation

## Arquitectura

El proyecto sigue una arquitectura por capas para separar responsabilidades:

```text
src/main/java/com/example/reservations/
│
├── controller/
│   ├── UserController.java
│   ├── RoomController.java
│   └── ReservationController.java
│
├── dto/
│   ├── user/
│   ├── room/
│   └── reservation/
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── EmailAlreadyExistsException.java
│   └── InvalidReservationException.java
│
├── model/
│   ├── User.java
│   ├── Room.java
│   └── Reservation.java
│
├── repository/
│   ├── UserRepository.java
│   ├── RoomRepository.java
│   └── ReservationRepository.java
│
├── service/
│   ├── UserService.java
│   ├── RoomService.java
│   └── ReservationService.java
│
└── ReservationsApplication.java
```

### Responsabilidades

| Capa | Responsabilidad |
|---|---|
| Controller | Recibir peticiones HTTP y devolver respuestas. |
| Service | Gestionar la lógica de negocio y las validaciones. |
| Repository | Acceder a la base de datos mediante JPA. |
| Model | Definir las entidades y relaciones. |
| DTO | Controlar los datos recibidos y devueltos por la API. |
| Exception | Gestionar los errores de forma centralizada. |

## Base de datos

La aplicación utiliza PostgreSQL y trabaja con tres entidades principales.

### Modelo relacional

**users**

| Campo | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | Primary Key |
| name | VARCHAR | NOT NULL |
| email | VARCHAR | NOT NULL, UNIQUE |

**rooms**

| Campo | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | Primary Key |
| name | VARCHAR | NOT NULL |
| capacity | INTEGER | NOT NULL |

**reservations**

| Campo | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | Primary Key |
| start_date_time | TIMESTAMPTZ | NOT NULL |
| end_date_time | TIMESTAMPTZ | NOT NULL |
| user_id | BIGINT | Foreign Key |
| room_id | BIGINT | Foreign Key |

### Relaciones

- Un usuario puede tener varias reservas.
- Una sala puede tener varias reservas.
- Cada reserva pertenece a un único usuario y a una única sala.

Las relaciones se gestionan mediante JPA/Hibernate utilizando `@ManyToOne` y `@JoinColumn`.

## Instalación y configuración

### 1. Requisitos previos

Antes de ejecutar el proyecto necesitas:

- Java JDK 21.
- Maven.
- PostgreSQL 16 o compatible.
- IntelliJ IDEA (opcional).

### 2. Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO
cd reservations
```

### 3. Crear la base de datos

Desde PostgreSQL o pgAdmin, ejecuta:

```sql
CREATE DATABASE room_reservations;
```

No es necesario crear manualmente las tablas, ya que Hibernate se encarga de generarlas y actualizarlas mediante la configuración de JPA.

### 4. Configurar las variables de entorno

En la raíz del proyecto, junto al archivo `pom.xml`, crea un archivo llamado `.env`.

```dotenv
DB_URL=jdbc:postgresql://localhost:5432/room_reservations
DB_USERNAME=postgres
DB_PASSWORD=TU_CONTRASEÑA
```

Sustituye los valores por los correspondientes a tu instalación de PostgreSQL.

**Importante:** el archivo `.env` contiene credenciales y no debe subirse a GitHub.

Asegúrate de incluirlo en `.gitignore`:

```gitignore
.env
```

### 5. Configuración de Spring Boot

El archivo `application.properties` utiliza las variables definidas en `.env`:

```properties
spring.application.name=reservations

spring.config.import=optional:file:./.env[.properties]

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

### 6. Ejecutar la aplicación

Desde IntelliJ IDEA, ejecuta la clase principal:

```text
ReservationsApplication.java
```

También puedes iniciar la aplicación desde Maven:

```bash
./mvnw spring-boot:run
```

La API estará disponible en:

```text
http://localhost:8080
```

## Endpoints de la API

Todos los endpoints utilizan JSON para enviar y recibir información.

### Usuarios

Ruta base: `/api/users`

| Método | Endpoint | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/users` | Obtener todos los usuarios. | 200 |
| GET | `/api/users/{id}` | Obtener un usuario por ID. | 200 |
| POST | `/api/users` | Crear un usuario. | 201 |
| PUT | `/api/users/{id}` | Actualizar un usuario. | 200 |
| DELETE | `/api/users/{id}` | Eliminar un usuario. | 204 |

#### Crear usuario

`POST /api/users`

Body:

```json
{
  "name": "Juan Pérez",
  "email": "juan@example.com"
}
```

Respuesta:

```json
{
  "id": 1,
  "name": "Juan Pérez",
  "email": "juan@example.com"
}
```

### Salas

Ruta base: `/api/rooms`

| Método | Endpoint | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/rooms` | Obtener todas las salas. | 200 |
| GET | `/api/rooms/{id}` | Obtener una sala por ID. | 200 |
| POST | `/api/rooms` | Crear una sala. | 201 |
| PUT | `/api/rooms/{id}` | Actualizar una sala. | 200 |
| DELETE | `/api/rooms/{id}` | Eliminar una sala. | 204 |

#### Crear sala

`POST /api/rooms`

Body:

```json
{
  "name": "Sala de reuniones",
  "capacity": 10
}
```

Respuesta:

```json
{
  "id": 1,
  "name": "Sala de reuniones",
  "capacity": 10
}
```

### Reservas

Ruta base: `/api/reservations`

| Método | Endpoint | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/reservations` | Obtener todas las reservas. | 200 |
| GET | `/api/reservations/{id}` | Obtener una reserva por ID. | 200 |
| POST | `/api/reservations` | Crear una reserva. | 201 |
| PUT | `/api/reservations/{id}` | Actualizar una reserva. | 200 |
| DELETE | `/api/reservations/{id}` | Eliminar una reserva. | 204 |

#### Crear reserva

`POST /api/reservations`

Body:

```json
{
  "startDateTime": "2026-11-03T10:00:00+01:00",
  "endDateTime": "2026-11-03T11:00:00+01:00",
  "userId": 1,
  "roomId": 1
}
```

Respuesta:

```json
{
  "id": 1,
  "startDateTime": "2026-11-03T09:00:00Z",
  "endDateTime": "2026-11-03T10:00:00Z",
  "userId": 1,
  "roomId": 1
}
```

Las fechas se gestionan mediante `OffsetDateTime` y PostgreSQL `TIMESTAMPTZ`. PostgreSQL almacena los instantes normalizados a UTC, por lo que la respuesta puede mostrar el sufijo `Z`.

**Validaciones de reservas:**

- La fecha de finalización debe ser posterior a la fecha de inicio.
- El usuario debe existir.
- La sala debe existir.
- No se permiten reservas con horarios solapados en una misma sala.
- Se permiten reservas consecutivas cuando una termina exactamente cuando empieza la siguiente.

## Gestión de errores

La aplicación utiliza un `GlobalExceptionHandler` mediante `@RestControllerAdvice` para centralizar el tratamiento de excepciones.

| Código HTTP | Error | Descripción |
|---|---|---|
| 400 | Bad Request | Datos inválidos o reglas de negocio incumplidas. |
| 404 | Not Found | El recurso solicitado no existe. |
| 409 | Conflict | Email duplicado o conflicto de integridad en la base de datos. |

### Ejemplos de errores

**400 - Validación incorrecta**

```json
{
  "name": "El nombre es obligatorio"
}
```

**404 - Recurso inexistente**

```json
{
  "error": "Not Found",
  "message": "No existe un usuario con ID: 99"
}
```

**409 - Email duplicado**

```json
{
  "error": "Conflict",
  "message": "El email juan@example.com ya está registrado."
}
```

## Pruebas de la API

Los endpoints se han probado manualmente utilizando Postman.

Se han comprobado:

- Operaciones CRUD de usuarios.
- Operaciones CRUD de salas.
- Operaciones CRUD de reservas.
- Validaciones de los DTOs.
- Gestión de recursos inexistentes.
- Detección de emails duplicados.
- Validación de fechas.
- Detección de solapamientos horarios.
- Persistencia de datos en PostgreSQL.

## Estado actual del proyecto

- [x] Configuración de Spring Boot.
- [x] Conexión con PostgreSQL.
- [x] Entidades JPA.
- [x] Relaciones entre entidades.
- [x] Repositorios.
- [x] DTOs y validaciones.
- [x] Servicios y lógica de negocio.
- [x] Controllers REST.
- [x] Gestión global de excepciones.
- [x] CRUD completo.
- [x] Control de solapamientos de reservas.
- [x] Pruebas manuales de endpoints.

## Autor

Proyecto desarrollado como parte de un plan de formación en desarrollo Full Stack, utilizando Java, Spring Boot, JPA y PostgreSQL.