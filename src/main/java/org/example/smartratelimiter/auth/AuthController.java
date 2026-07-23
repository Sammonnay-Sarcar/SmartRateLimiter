package org.example.smartratelimiter.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServices authServices;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest requestBody){
        System.out.println("SSUP");
        String token = authServices.register(requestBody.emailId(),requestBody.password());
        return ResponseEntity.ok(token);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest requestBody){
        try {
            String token = authServices.login(requestBody.emailId(),requestBody.password());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

