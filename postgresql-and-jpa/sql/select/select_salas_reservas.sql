
-- Muestra todas las salas y sus reservas, incluidas las que no tienen

SELECT
    r.name AS salas,
    COUNT(res.id) AS numero_reservas
FROM rooms r
         LEFT JOIN reservations res ON res.room_id = r.id
GROUP BY r.id, r.name
ORDER BY numero_reservas DESC;
