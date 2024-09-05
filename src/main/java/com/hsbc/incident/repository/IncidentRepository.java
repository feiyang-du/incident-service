package com.hsbc.incident.repository;

import com.hsbc.incident.model.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    boolean existsByTitle(String title); // Check exist incident title
}
