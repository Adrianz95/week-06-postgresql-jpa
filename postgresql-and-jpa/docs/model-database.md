
# Decisiones del modelo de base de datos

## 1. ¿Por qué reservations tiene una clave foránea hacia users?

La tabla reservations necesita una clave foránea hacia users porque cada reserva debe estar asociada a un usuario existente. 
De esta manera, garantizamos la integridad referencial y evitamos crear reservas para usuarios que no existen.

## 2. ¿Por qué también necesita una clave foránea hacia rooms?

Porque cada reserva debe corresponder a una sala existente. 
La clave foránea permite relacionar la reserva con la sala que se quiere ocupar y evita que se asignen reservas a salas inexistentes.

## 3. ¿Qué ocurriría si intentaras crear una reserva con user_id = 9999 y ese usuario no existiera?

Si la columna user_id tiene una clave foránea correctamente configurada, PostgreSQL rechazará la inserción porque no encontrará ningún usuario con ese identificador. 
Así se evita almacenar una reserva que haga referencia a un usuario inexistente.

## 4. ¿Qué ventaja aporta la restricción CHECK?

La restricción CHECK permite definir condiciones que los datos deben cumplir antes de ser guardados. 
Por ejemplo, en la tabla rooms podemos exigir que la capacidad sea mayor que cero. 
Si se intenta introducir una capacidad igual o inferior a cero, PostgreSQL rechazará la operación.

## 5. ¿Cómo evitarías que dos reservas ocuparan la misma sala en horarios que se solapan?

Controlaría los solapamientos directamente en PostgreSQL mediante una restricción de exclusión (EXCLUDE). Para ello, cada reserva tendría una sala asociada y un intervalo temporal definido por la fecha y hora de inicio y de fin.
La restricción impediría que existieran dos reservas para la misma sala cuyos intervalos temporales se solaparan. 
De esta manera, la propia base de datos garantizaría la integridad de las reservas, incluso si se intentara realizar una operación desde otra aplicación.
También gestionaría en Spring Boot el error producido por la base de datos para devolver una respuesta adecuada al usuario.
