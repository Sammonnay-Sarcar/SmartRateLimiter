package org.example.smartratelimiter.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.smartratelimiter.logging.RequestLogRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
public class AnalyticsController {
    private final RequestLogRepository requestLogRepository;
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        List<Object[]> topUsers = requestLogRepository.getTopUsers();
        List<Object[]> slowestEndpoints = requestLogRepository.getSlowestEndpoints();
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("topUsers", topUsers);
        summary.put("slowestEndpoints", slowestEndpoints);
        
        return ResponseEntity.ok(summary);
    }
    
}
