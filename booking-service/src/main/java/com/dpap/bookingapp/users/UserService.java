package com.dpap.bookingapp.users;

import com.dpap.bookingapp.users.dto.EmailRequest;
import com.dpap.bookingapp.users.dto.PasswordRequest;
import com.dpap.bookingapp.users.dto.RegisterUserRequest;
import com.dpap.bookingapp.users.dto.UserQueryDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDatabase userDatabase;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDatabase userDatabase, PasswordEncoder passwordEncoder) {
        this.userDatabase = userDatabase;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserQueryDto> getAllUsers() {
        return userDatabase.findAllUsers()
                .stream()
                .map(user -> new UserQueryDto(user.getId(), user.getUsername())
                ).
                collect(Collectors.toList());
    }

    public Optional<UserQueryDto> findByUsername(String username) {
        var user = userDatabase.findByUsername(username);
        return user.map(value -> new UserQueryDto(value.getId(), value.getUsername()));
    }

    @Transactional
    public void changeEmail(EmailRequest emailRequest, UserQueryDto loggedUser) {
        if (!userDatabase.existsByEmail(emailRequest.email()))
            userDatabase.changeEmail(emailRequest.email(), loggedUser.id());
            userDatabase.changeUsername(emailRequest.email(), loggedUser.id());
    }

    @Transactional
    public void changePassword(PasswordRequest passwordRequest, UserQueryDto loggedUser) {
        userDatabase.changePassword(passwordEncoder.encode(passwordRequest.password()), loggedUser.id());
    }

    public void takeAdminRoleFromUser(String username) {
    }

    public void deleteLoggedUser() {
    }

    public void deleteUserByUsername(String username) {
    }

    public void updateLoggedUser(RegisterUserRequest registerUserRequest) {
    }

    public void giveAdminRoleToUser(String username) {
    }

    public void updateUserByUsername(String username, RegisterUserRequest registerUserRequest) {
    }

    public void createUser(RegisterUserRequest registerUserRequest) {

    }

    public Optional<User> findByUserId(Long id) {
        return userDatabase.findById(id);
    }
}
