package com.alikhansfamily.registrator.repository;

import com.alikhansfamily.registrator.model.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementRepository extends JpaRepository<Management, Long> {
}
