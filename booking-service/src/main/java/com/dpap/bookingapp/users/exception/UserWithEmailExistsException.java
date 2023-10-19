package com.dpap.bookingapp.users.exception;

public class UserWithEmailExistsException extends RuntimeException {
    UserWithEmailExistsException(String email) {
        super("User with email: %s exists".formatted(email));
    }
}
