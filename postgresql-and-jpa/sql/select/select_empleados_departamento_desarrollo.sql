
-- Muestra los trabajadores del departamento de desarrollo

SELECT name, department
FROM users
WHERE department ILIKE '%desarrollo';
