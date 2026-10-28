
-- Muestra las reservas confirmadas

SELECT
    r.name AS room,
    status AS estado
FROM reservations res
         INNER JOIN rooms r ON res.room_id = r.id
WHERE status ILIKE '%confirmed%';
