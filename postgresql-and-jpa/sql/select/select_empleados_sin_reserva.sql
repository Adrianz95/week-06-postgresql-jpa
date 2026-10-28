
-- Muestra los empleados que no tienen reserva

SELECT
    u.name AS empleado,
    COUNT(res.id) AS reservas
FROM users u
         LEFT JOIN reservations res ON res.user_id = u.id
GROUP BY u.id, u.name
ORDER BY reservas DESC;
