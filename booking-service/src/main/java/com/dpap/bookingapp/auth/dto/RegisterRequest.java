package com.dpap.bookingapp.auth.dto;

public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password
) { }
