package com.dpap.bookingapp.auth.dto;

public record AuthenticationRequest(
        String username, String password
) { }
