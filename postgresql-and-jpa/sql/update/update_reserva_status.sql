
-- Actualizamos el estado de una reserva

UPDATE reservations
SET status = 'CANCELLED'
WHERE id = 4;
