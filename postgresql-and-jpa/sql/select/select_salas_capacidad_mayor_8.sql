SELECT 
	u.name AS employee, 
	COUNT(*) AS total
FROM reservations res
INNER JOIN users u ON res.user_id = u.id
INNER JOIN rooms r ON res.room_id = r.id
GROUP BY u.id
ORDER BY total DESC;