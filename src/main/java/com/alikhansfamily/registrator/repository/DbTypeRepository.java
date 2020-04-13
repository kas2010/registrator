package com.alikhansfamily.registrator.repository;

import com.alikhansfamily.registrator.model.DbType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbTypeRepository extends JpaRepository<DbType, Long> {
}
