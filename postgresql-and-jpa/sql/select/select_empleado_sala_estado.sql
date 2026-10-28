
-- Muestra el nombre del empleado, nombre de la sala y el estado de cada reserva

SELECT
    u.name AS employee,
    r.name AS room,
    status AS estado
FROM reservations res
         INNER JOIN users u ON res.user_id = u.id
         INNER JOIN rooms r ON res.room_id = r.id;
