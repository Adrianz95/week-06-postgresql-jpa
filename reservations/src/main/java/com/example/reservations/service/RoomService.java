package com.example.reservations.service;

import com.example.reservations.exception.ResourceNotFoundException;
import com.example.reservations.model.Room;
import com.example.reservations.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una sala con ID: " + id
                ));
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Long id, Room roomDetails) {

        Room room = findById(id);

        room.setName(roomDetails.getName());
        room.setCapacity(roomDetails.getCapacity());

        return roomRepository.save(room);
    }

    public void deleteById(Long id) {
        Room room = findById(id);
        roomRepository.delete(room);
    }
}