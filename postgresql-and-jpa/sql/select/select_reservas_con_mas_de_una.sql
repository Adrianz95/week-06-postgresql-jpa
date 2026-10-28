
-- Muestra únicamente los estados que tengan al menos dos reservas

SELECT status AS estado, COUNT(*) AS total
FROM reservations
GROUP BY status
HAVING COUNT(*) > 1;
