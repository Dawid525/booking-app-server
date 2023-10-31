package com.dpap.bookingapp.users.dto;

import jakarta.validation.constraints.Pattern;

public record PasswordRequest(
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
        String password
) {
}

