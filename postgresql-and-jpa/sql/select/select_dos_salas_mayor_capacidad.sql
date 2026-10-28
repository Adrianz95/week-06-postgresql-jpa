
-- Muestra las dos salas con mayor capacidad

SELECT name, capacity
FROM rooms
ORDER BY capacity DESC
    LIMIT 2;
