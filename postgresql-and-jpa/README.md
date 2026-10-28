
# Sistema de reservas de salas

Proyecto formativo para aprender PostgreSQL y JPA mediante el desarrollo de una aplicación de reservas de salas de reuniones.

## Tecnologías

- Java
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- SQL
- Git / GitHub

## Objetivo del proyecto

Desarrollar progresivamente una aplicación de reservas de salas para practicar el diseño y la gestión de bases de datos relacionales, así como su futura integración con una API REST desarrollada con Spring Boot.

Durante esta iteración se trabaja con consultas SQL para recuperar, filtrar, relacionar y modificar datos.

## Base de datos

La primera iteración utiliza tres tablas.

### `users`

Almacena la información de los usuarios de la aplicación.

| Columna | Descripción |
|---|---|
| `id` | Identificador del usuario |
| `name` | Nombre del usuario |
| `department` | Departamento al que pertenece |

### `rooms`

Almacena las salas disponibles para realizar reuniones.

| Columna | Descripción |
|---|---|
| `id` | Identificador de la sala |
| `name` | Nombre de la sala |
| `capacity` | Capacidad máxima de la sala |

### `reservations`

Almacena las reservas realizadas por los usuarios.

| Columna | Descripción |
|---|---|
| `id` | Identificador de la reserva |
| `user_id` | Usuario que realiza la reserva |
| `room_id` | Sala reservada |
| `status` | Estado de la reserva |
| `reserved_at` | Fecha y hora de creación de la reserva |

Las columnas `user_id` y `room_id` permiten relacionar las reservas con las tablas `users` y `rooms`, respectivamente.

## Preparación del entorno

### 1. Crear la base de datos

Crear una base de datos PostgreSQL llamada `reservas_practica` desde pgAdmin o mediante el cliente `psql`.

### 2. Ejecutar el script de preparación

El script de preparación crea las tablas e inserta los datos de prueba.

Si el archivo se encuentra en `sql/setup.sql`, ejecutar:

```bash
psql -U postgres -d reservas_practica -f sql/setup.sql
```

> Si utilizas otro nombre o ubicación para el archivo, adapta la ruta del comando.

Si las tablas ya existen, utiliza una base de datos limpia o prepara el entorno antes de volver a ejecutar el script.

### 3. Comprobar los datos

Una vez ejecutado el script, comprobar que las tablas contienen los registros esperados:

```sql
SELECT * FROM users;

SELECT * FROM rooms;

SELECT * FROM reservations;
```

## Consultas SQL implementadas

### 1. Consultas y filtros

Se utiliza `SELECT` para recuperar información y `WHERE` para aplicar condiciones.

Ejemplo: buscar salas con capacidad igual o superior a ocho personas.

```sql
SELECT id, name, capacity
FROM rooms
WHERE capacity >= 8;
```

También se practican:

- Operadores de comparación.
- Condiciones combinadas con `AND` y `OR`.
- Búsquedas con `LIKE` e `ILIKE`.
- Comprobaciones de valores nulos con `IS NULL` e `IS NOT NULL`.

### 2. Ordenación y limitación de resultados

Se utilizan `ORDER BY` y `LIMIT` para ordenar y limitar los resultados.

Ejemplo: obtener las dos salas con mayor capacidad.

```sql
SELECT id, name, capacity
FROM rooms
ORDER BY capacity DESC
LIMIT 2;
```

### 3. Agrupaciones y funciones de agregación

Se practican `GROUP BY`, `HAVING` y las funciones `COUNT`, `SUM`, `AVG`, `MIN` y `MAX`.

Ejemplo: contar las reservas por estado.

```sql
SELECT status, COUNT(*) AS total
FROM reservations
GROUP BY status;
```

Ejemplo: mostrar únicamente los estados con dos o más reservas.

```sql
SELECT status, COUNT(*) AS total
FROM reservations
GROUP BY status
HAVING COUNT(*) >= 2;
```

### 4. Relaciones entre tablas con JOIN

Se utilizan `INNER JOIN` y `LEFT JOIN` para consultar información relacionada entre usuarios, salas y reservas.

Ejemplo: mostrar el empleado, la sala y el estado de cada reserva.

```sql
SELECT
    u.name AS employee,
    r.name AS room,
    res.status
FROM reservations res
INNER JOIN users u
    ON res.user_id = u.id
INNER JOIN rooms r
    ON res.room_id = r.id;
```

También se estudia `LEFT JOIN` para incluir registros que no tienen coincidencias en la tabla relacionada.

### 5. Inserción, actualización y eliminación

Se practican las operaciones básicas de manipulación de datos.

**Insertar una sala:**

```sql
INSERT INTO rooms (name, capacity)
VALUES ('Sala Este', 6);
```

**Actualizar la capacidad de una sala:**

```sql
UPDATE rooms
SET capacity = 5
WHERE id = 1;
```

**Actualizar el estado de una reserva:**

```sql
UPDATE reservations
SET status = 'CANCELLED'
WHERE id = 4;
```

**Eliminar una sala:**

```sql
DELETE FROM rooms
WHERE id = 4;
```

Antes de ejecutar operaciones de modificación o eliminación, se debe comprobar qué registros resultarán afectados. Las claves foráneas también pueden impedir que se eliminen salas que tengan reservas relacionadas.

## Resultados esperados

Con los datos de prueba iniciales, los resultados esperados son:

| Consulta | Resultado esperado |
|---|---|
| Salas con capacidad igual o superior a 8 | Sala Sur y Sala Central |
| Dos salas con mayor capacidad | Sala Central y Sala Sur |
| Reservas agrupadas por estado | 2 confirmadas, 1 cancelada y 1 pendiente |
| Estados con al menos dos reservas | CONFIRMED |
| Reservas con información del empleado y la sala | 4 registros |
| Reservas del usuario con `id = 1` | 2 registros |

Estos resultados sirven como referencia para validar las consultas y deben contrastarse con la ejecución real en PostgreSQL.

## Pruebas manuales

Durante el desarrollo se deben comprobar los siguientes escenarios:

- Las consultas devuelven los registros esperados.
- Los filtros sin coincidencias no devuelven filas.
- Los agrupamientos muestran los totales correctos.
- Los `LEFT JOIN` incluyen las salas o los empleados sin reservas.
- Las operaciones `INSERT` y `UPDATE` modifican los datos correctamente.
- Las operaciones `DELETE` afectan únicamente al registro previsto.
- Las restricciones de las tablas impiden operaciones incompatibles con las relaciones existentes.

## Problemas encontrados y soluciones

### Ejecución repetida del script

**Problema:** ejecutar de nuevo el script de preparación puede provocar errores si las tablas ya existen.

**Solución:** utilizar una base de datos limpia o preparar el entorno antes de volver a crear las tablas.

### Modificaciones accidentales de datos

**Problema:** ejecutar `UPDATE` o `DELETE` sin una condición `WHERE` puede afectar a todos los registros de una tabla.

**Solución:** ejecutar primero un `SELECT` con la misma condición y verificar los registros afectados.

### Consultas con tablas relacionadas

**Problema:** una consulta puede devolver información incompleta si no se utilizan correctamente las condiciones del `JOIN`.

**Solución:** comprobar las claves utilizadas para relacionar las tablas y validar los resultados.

### Detección de conflictos entre reservas

**Problema:** el esquema inicial no incluye fechas de inicio y finalización de las reservas, por lo que todavía no permite comprobar correctamente los solapamientos horarios.

**Solución prevista:** estudiar la ampliación del modelo de datos para incorporar los intervalos de las reservas durante el diseño relacional.

## Estado actual

- [x] Crear las tablas iniciales.
- [x] Insertar datos de prueba.
- [x] Estudiar consultas SQL, filtros y ordenación.
- [x] Estudiar agrupaciones y funciones de agregación.
- [x] Estudiar `INNER JOIN` y `LEFT JOIN`.
- [x] Practicar inserciones, actualizaciones y eliminaciones.
- [ ] Completar y validar todas las consultas prácticas.
- [ ] Diseñar relaciones y restricciones adicionales.
- [ ] Integrar Spring Boot con PostgreSQL.
- [ ] Implementar entidades y relaciones con JPA / Hibernate.
- [ ] Implementar la API REST.
- [ ] Documentar y probar los endpoints.

## Próximos pasos

1. Completar y validar las consultas SQL en PostgreSQL.
2. Diseñar el modelo relacional y estudiar las restricciones, los índices y las transacciones.
3. Configurar la conexión entre Spring Boot y PostgreSQL.
4. Crear las entidades `User`, `Room` y `Reservation`.
5. Implementar los repositorios, servicios y controladores de la API REST.
6. Añadir pruebas y documentar el funcionamiento de la aplicación.

## Organización del proyecto

```text
week-06/
├── sql/
│   ├── setup.sql
│   └── queries.sql
├── docs/
│   └── sql-tests.md
└── README.md
```

Esta estructura es orientativa y puede adaptarse a la organización actual del repositorio.

---

**Proyecto formativo:** PostgreSQL + JPA

**Objetivo final:** construir una API REST de reservas de salas utilizando Java, Spring Boot, PostgreSQL y JPA / Hibernate.
