package com.example.reservations.controller;

import com.example.reservations.dto.room.RoomRequest;
import com.example.reservations.dto.room.RoomResponse;
import com.example.reservations.model.Room;
import com.example.reservations.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll() {
        List<RoomResponse> rooms = roomService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        Room room = roomService.findById(id);
        return ResponseEntity.ok(toResponse(room));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> save(@Valid @RequestBody RoomRequest request) {
        Room room = new Room(request.name(), request.capacity());
        Room savedRoom = roomService.save(room);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(savedRoom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {

        Room room = new Room(request.name(), request.capacity());
        Room updatedRoom = roomService.update(id, room);

        return ResponseEntity.ok(toResponse(updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getCapacity()
        );
    }
}