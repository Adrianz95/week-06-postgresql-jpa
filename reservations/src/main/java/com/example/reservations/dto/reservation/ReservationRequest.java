
package com.example.reservations.dto.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record ReservationRequest(

        @NotNull(message = "La fecha de inicio es obligatoria")
        OffsetDateTime startDateTime,

        @NotNull(message = "La fecha de finalización es obligatoria")
        OffsetDateTime endDateTime,

        @NotNull(message = "El usuario es obligatorio")
        Long userId,

        @NotNull(message = "La sala es obligatoria")
        Long roomId

) {
}
