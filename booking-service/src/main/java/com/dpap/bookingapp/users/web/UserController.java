package com.dpap.bookingapp.users.web;

import com.dpap.bookingapp.auth.AuthenticationService;
import com.dpap.bookingapp.users.User;
import com.dpap.bookingapp.users.UserDatabase;
import com.dpap.bookingapp.users.exception.UserNotFoundException;
import com.dpap.bookingapp.users.UserService;
import com.dpap.bookingapp.users.dto.EmailRequest;
import com.dpap.bookingapp.users.dto.PasswordRequest;
import com.dpap.bookingapp.users.dto.RegisterUserRequest;
import com.dpap.bookingapp.users.dto.UserQueryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationUserService;
    private final UserDatabase userDatabase;

    public UserController(UserService userService, AuthenticationService authenticationUserService, UserDatabase userDatabase) {
        this.userService = userService;
        this.authenticationUserService = authenticationUserService;
        this.userDatabase = userDatabase;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserQueryDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me/info")
    ResponseEntity<UserQueryDto> getInfoAboutMe() {
        var user = authenticationUserService.getLoggedUser();
        return ResponseEntity.ok(userService.findByUsername(user.username()).get());

    }


    @GetMapping("/{username}/info")
    ResponseEntity<UserQueryDto> getInfoAboutUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> addUser(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.createUser(registerUserRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userDatabase.findById(id).orElseThrow(() -> new RuntimeException("")));
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody RegisterUserRequest registerUserRequest) {
        userService.updateUserByUsername(username, registerUserRequest);
        return ResponseEntity.status(204).build();
    }

    @PutMapping()
    ResponseEntity<?> updateLoggedUser(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.updateLoggedUser(registerUserRequest);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{username}/give/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> giveAdminRoleToUser(@PathVariable String username) {
        userService.giveAdminRoleToUser(username);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{username}/take/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> takeAdminRoleFromUser(@PathVariable String username) {
        userService.takeAdminRoleFromUser(username);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/change/password")
    ResponseEntity<?> changePasswordByLoggedUser(@RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(passwordRequest);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/change/email")
    ResponseEntity<?> changeEmailByLoggedUser(@RequestBody EmailRequest emailRequest) {
        userService.changeEmail(emailRequest);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping()
    ResponseEntity<?> deleteLoggedUser() {
        userService.deleteLoggedUser();
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.status(204).build();
    }
}