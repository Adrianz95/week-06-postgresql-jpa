package com.example.reservations.dto.room;

public record RoomResponse(
        Long id,
        String name,
        Integer capacity
) {
}
