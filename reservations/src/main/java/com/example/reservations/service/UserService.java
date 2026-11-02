package com.example.reservations.service;

import com.example.reservations.exception.EmailAlreadyExistsException;
import com.example.reservations.exception.ResourceNotFoundException;
import com.example.reservations.model.User;
import com.example.reservations.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un usuario con ID: " + id
                ));
    }

    public User save(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "El email " + user.getEmail() + " ya está registrado."
            );
        }

        return userRepository.save(user);
    }

    public User update(Long id, User userDetails) {

        User user = findById(id);

        if (userRepository.existsByEmail(userDetails.getEmail())
                && !user.getEmail().equalsIgnoreCase(userDetails.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "El email " + userDetails.getEmail() + " ya está registrado."
            );
        }

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

}
