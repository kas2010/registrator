package com.alikhansfamily.registrator.repository;

import com.alikhansfamily.registrator.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {
}
