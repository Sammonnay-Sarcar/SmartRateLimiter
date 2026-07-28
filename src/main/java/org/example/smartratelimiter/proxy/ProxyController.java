package org.example.smartratelimiter.proxy;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.example.smartratelimiter.ratelimit.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class ProxyController {
    private final RateLimiterService rateLimiterService;
    private final WebClient webClient = WebClient.create();
    @GetMapping("/proxy/**")
    public ResponseEntity<?> getMethodName(HttpServletRequest request, Principal principal) {
        String username = principal.getName();
        if (!rateLimiterService.isAllowed(username)) {
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }
        String uri = request.getRequestURI().replace("/proxy", "");
        String targetUri = "http://httpbin.org" + uri;
        String response = webClient.get()
                .uri(targetUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return ResponseEntity.ok(response);
    }    
}
