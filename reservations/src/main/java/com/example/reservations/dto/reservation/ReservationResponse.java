
package com.example.reservations.dto.reservation;

import java.time.OffsetDateTime;

public record ReservationResponse(
        Long id,
        OffsetDateTime startDateTime,
        OffsetDateTime endDateTime,
        Long userId,
        Long roomId
) {
}
