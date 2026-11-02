package com.example.reservations.repository;

import com.example.reservations.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByRoom_IdAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            Long roomId,
            OffsetDateTime endDateTime,
            OffsetDateTime startDateTime
    );

    boolean existsByRoom_IdAndStartDateTimeLessThanAndEndDateTimeGreaterThanAndIdNot(
            Long roomId,
            OffsetDateTime endDateTime,
            OffsetDateTime startDateTime,
            Long reservationId
    );
}
