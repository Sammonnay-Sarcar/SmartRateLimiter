package org.example.smartratelimiter.ratelimit;

import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenBucketRateLimiterService {
    private final StringRedisTemplate redis;
    private static final int MAX_TOKENS = 10;
    private static final double REFILL_RATE = 10.0 / 60.0;
    public boolean isAllowed(String username) {
        String key = "token_bucket:" + username;
        Map<Object, Object> bucketData = redis.opsForHash().entries(key);
        if(bucketData.isEmpty()) {
            redis.opsForHash().put(key, "tokens", String.valueOf(MAX_TOKENS-1));
            redis.opsForHash().put(key, "last_refill_time", String.valueOf(System.currentTimeMillis()));
            return true;
        }

        double tokens = Double.parseDouble((String) bucketData.get("tokens"));
        long lastRefill = Long.parseLong((String) bucketData.get("last_refill_time"));

        long now = System.currentTimeMillis();
        double elapsed = (now - lastRefill) / 1000.0; // convert to seconds
        double tokensToAdd = elapsed * REFILL_RATE;
        tokens = Math.min(MAX_TOKENS, tokens + tokensToAdd);

        if (tokens >= 1) {
            redis.opsForHash().put(key, "tokens", String.valueOf(tokens - 1));
            redis.opsForHash().put(key, "last_refill_time", String.valueOf(now));
            return true;
        }
        return false;
    }
}
