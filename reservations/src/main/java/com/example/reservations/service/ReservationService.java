package com.example.reservations.service;

import com.example.reservations.dto.reservation.ReservationRequest;
import com.example.reservations.dto.reservation.ReservationResponse;
import com.example.reservations.exception.InvalidReservationException;
import com.example.reservations.exception.ResourceNotFoundException;
import com.example.reservations.model.Reservation;
import com.example.reservations.model.Room;
import com.example.reservations.model.User;
import com.example.reservations.repository.ReservationRepository;
import com.example.reservations.repository.RoomRepository;
import com.example.reservations.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una reserva con ID: " + id
                ));
    }

    public Reservation save(ReservationRequest request) {

        if (reservationRepository
                .existsByRoom_IdAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                        request.roomId(),
                        request.endDateTime(),
                        request.startDateTime())) {

            throw new InvalidReservationException(
                    "La sala ya tiene una reserva en ese horario.");
        }

        validateDates(request);

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un usuario con ID: " + request.userId()
                ));

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una sala con ID: " + request.roomId()
                ));

        Reservation reservation = new Reservation(
                request.startDateTime(),
                request.endDateTime()
        );

        reservation.setUser(user);
        reservation.setRoom(room);

        return reservationRepository.save(reservation);
    }

    public Reservation update(Long id, ReservationRequest request) {

        if (reservationRepository
                .existsByRoom_IdAndStartDateTimeLessThanAndEndDateTimeGreaterThanAndIdNot(
                        request.roomId(),
                        request.endDateTime(),
                        request.startDateTime(),
                        id)) {

            throw new InvalidReservationException(
                    "La sala ya tiene una reserva en ese horario.");
        }

        Reservation reservation = findById(id);

        validateDates(request);

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un usuario con ID: " + request.userId()
                ));

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una sala con ID: " + request.roomId()
                ));

        reservation.setStartDateTime(request.startDateTime());
        reservation.setEndDateTime(request.endDateTime());
        reservation.setUser(user);
        reservation.setRoom(room);

        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id);
        reservationRepository.deleteById(reservation.getId());
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getStartDateTime()
                        .withOffsetSameInstant(ZoneOffset.of("Europe/Madrid")),
                reservation.getEndDateTime()
                        .withOffsetSameInstant(ZoneOffset.of("Europe/Madrid")),
                reservation.getUser().getId(),
                reservation.getRoom().getId()
        );
    }

    private void validateDates(ReservationRequest request) {

        if (!request.endDateTime().isAfter(request.startDateTime())) {
            throw new InvalidReservationException(
                    "La fecha de finalización debe ser posterior a la fecha de inicio."
            );
        }
    }

}
