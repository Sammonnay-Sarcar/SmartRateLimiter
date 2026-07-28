package org.example.smartratelimiter.ratelimit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final StringRedisTemplate redisTemplate;
    private static final int MAX_COUNT = 10; // Maximum allowed requests
    public boolean isAllowed(String username){
        String key = "rate_limit:" + username;
        Long count = redisTemplate.opsForValue().increment(key,1);
        if (count == 1) {
            // Set expiration time for the key (e.g., 1 minute)
            redisTemplate.expire(key, java.time.Duration.ofMinutes(1));
        }
        return count <= MAX_COUNT;
    }
}
