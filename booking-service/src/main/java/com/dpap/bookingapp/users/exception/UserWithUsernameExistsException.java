package com.dpap.bookingapp.users.exception;

public class UserWithUsernameExistsException extends RuntimeException {
    UserWithUsernameExistsException(String username) {
        super("User with email: %s exists".formatted(username));
    }
}
