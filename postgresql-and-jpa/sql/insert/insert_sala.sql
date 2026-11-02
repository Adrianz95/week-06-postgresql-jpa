SELECT
u.name AS empleado,
COUNT(res.user_id) AS reservas_salas
FROM users u
LEFT JOIN reservations res ON u.id = res.user_id
GROUP BY u.id, u.name;