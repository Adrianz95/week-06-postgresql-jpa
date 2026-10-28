
-- Muestra el total de reservas por estado

SELECT status AS Estado, COUNT(*) AS Total_de_reservas
FROM reservations
GROUP BY status;
