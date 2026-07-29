package org.example.smartratelimiter.logging;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, UUID> {
}