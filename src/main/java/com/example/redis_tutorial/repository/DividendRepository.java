package com.example.redis_tutorial.repository;

import com.example.redis_tutorial.domain.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DividendRepository extends JpaRepository<Dividend, Long> {
    List<Dividend> findByCompanyId(Long companyId);
    boolean existsByCompanyIdAndDateTime(Long companyId, LocalDateTime dateTime);
}
