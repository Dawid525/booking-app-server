package com.dpap.bookingapp.users;

import com.dpap.bookingapp.users.dto.EmailRequest;
import com.dpap.bookingapp.users.dto.PasswordRequest;
import com.dpap.bookingapp.users.dto.RegisterUserRequest;
import com.dpap.bookingapp.users.dto.UserQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDatabase userDatabase;

    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
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

    public void changeEmail(EmailRequest emailRequest) {
    }

    public void changePassword(PasswordRequest passwordRequest) {
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
