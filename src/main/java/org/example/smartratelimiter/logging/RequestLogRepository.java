package org.example.smartratelimiter.logging;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, UUID> {
    @Query(value = """
    SELECT username, COUNT(*) as request_count 
    FROM request_logs 
    WHERE timestamp > NOW() - INTERVAL '1 hour'
    GROUP BY username 
    ORDER BY request_count DESC 
    LIMIT 5
    """, nativeQuery = true)
    public List<Object[]> getTopUsers();
    @Query(value = """
    SELECT endpoint, AVG(response_time_ms) as avg_response_time
    FROM request_logs
    GROUP BY endpoint
    ORDER BY avg_response_time DESC
    LIMIT 5
    """, nativeQuery = true)
    public List<Object[]> getSlowestEndpoints();
}