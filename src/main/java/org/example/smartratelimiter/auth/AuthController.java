package org.example.smartratelimiter.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServices authServices;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest requestBody){
        System.out.println("SSUP");
        authServices.register(requestBody.emailId(),requestBody.password());
        return ResponseEntity.ok("User registered successfully");
    }
}

