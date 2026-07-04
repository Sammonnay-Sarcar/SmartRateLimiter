package org.example.smartratelimiter.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServices {
    private  final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(String emailId, String password){
        User user = User.builder()
                .emailId(emailId)
                .passwordHash(bCryptPasswordEncoder.encode(password))
                .build();
        return userRepository.save(user);
    }
}
