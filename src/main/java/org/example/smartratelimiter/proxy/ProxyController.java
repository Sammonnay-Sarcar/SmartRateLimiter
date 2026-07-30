package org.example.smartratelimiter.proxy;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.example.smartratelimiter.logging.RequestLogService;
import org.example.smartratelimiter.ratelimit.RateLimiterService;
import org.example.smartratelimiter.ratelimit.TokenBucketRateLimiterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class ProxyController {
    private final RateLimiterService fixedWindowRateLimiterService;
    private final TokenBucketRateLimiterService tokenBucketRateLimiterService;
    private final WebClient webClient = WebClient.create();
    private final RequestLogService requestLogService;
    @Value("${rate.limiter.algorithm}")
    private String algorithm;
    @GetMapping("/proxy/**")
    public ResponseEntity<?> getMethodName(HttpServletRequest request, Principal principal) {
        String username = principal.getName();
        if (algorithm.equals("fixed_window")) {
            if (!fixedWindowRateLimiterService.isAllowed(username)) {
                return ResponseEntity.status(429).body("Rate limit exceeded");
            }
        } else if (algorithm.equals("token_bucket")) {
            if (!tokenBucketRateLimiterService.isAllowed(username)) {
                return ResponseEntity.status(429).body("Rate limit exceeded");
            }
        }
        String uri = request.getRequestURI().replace("/proxy", "");
        String targetUri = "http://httpbin.org" + uri;
        long startTime = System.nanoTime();
        String response = webClient.get()
                .uri(targetUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        long durationInMillis = (System.nanoTime() - startTime) / 1_000_000;
        requestLogService.log(username, targetUri, 200, durationInMillis);
        return ResponseEntity.ok(response);
    }    
}
