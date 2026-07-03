package org.example.smartratelimiter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

@SpringBootTest
class SmartRateLimiterApplicationTests {
    @BeforeAll
    static void setup() {
        // Force the test JVM to use a globally accepted zone string
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }
    @Test
    void contextLoads() {
    }

}
