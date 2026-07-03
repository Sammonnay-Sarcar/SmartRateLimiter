package org.example.smartratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SmartRateLimiterApplication {
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }
    public static void main(String[] args) {
        SpringApplication.run(SmartRateLimiterApplication.class, args);
    }
}
