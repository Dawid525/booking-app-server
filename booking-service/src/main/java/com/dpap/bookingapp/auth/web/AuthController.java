package com.dpap.bookingapp.auth.web;


import com.dpap.bookingapp.auth.AuthenticationService;
import com.dpap.bookingapp.auth.dto.AuthenticationRequest;
import com.dpap.bookingapp.auth.dto.AuthenticationResponse;
import com.dpap.bookingapp.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:4200")
@SecurityRequirements
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @GetMapping("/check/email")
    public ResponseEntity<Boolean> login(@RequestParam String email) {
        return ResponseEntity.ok(authenticationService.checkEmailUniqueness(email));
    }
}
