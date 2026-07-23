package org.example.smartratelimiter.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServices {
    private  final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    public String register(String emailId, String password){
         if (userRepository.findByEmailId(emailId).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = User.builder()
                .emailId(emailId)
                .passwordHash(bCryptPasswordEncoder.encode(password))
                .build();

        userRepository.save(user);
        return jwtService.generateToken(emailId);
    }
    public String login(String emailId, String password) {
    User user = userRepository.findByEmailId(emailId)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    
    if (!bCryptPasswordEncoder.matches(password, user.getPasswordHash())) {
        throw new RuntimeException("Invalid credentials");
    }
    
    return jwtService.generateToken(emailId);
}
}
