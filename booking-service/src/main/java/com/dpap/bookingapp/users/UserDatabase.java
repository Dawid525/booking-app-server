package com.dpap.bookingapp.users;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDatabase {
    Optional<User> findByUsername(String username);
    User save(User user);

    List<User> findAllUsers();

    Optional<User> findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
