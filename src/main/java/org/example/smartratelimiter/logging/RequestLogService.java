package org.example.smartratelimiter.logging;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    @Async
public void log(String username, String endpoint, int statusCode, long responseTimeMs) {
    try {
        RequestLog requestLog = RequestLog.builder()
                .username(username)
                .endpoint(endpoint)
                .statusCode(statusCode)
                .responseTimeMs(responseTimeMs)
                .timestamp(LocalDateTime.now())
                .build();
        requestLogRepository.save(requestLog);
    } catch (Exception e) {
        System.out.println("ASYNC LOG FAILED: " + e.getMessage());
    }
}
}