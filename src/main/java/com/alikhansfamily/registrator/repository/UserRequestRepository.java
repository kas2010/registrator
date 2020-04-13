package com.alikhansfamily.registrator.repository;

import com.alikhansfamily.registrator.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long>, JpaSpecificationExecutor<UserRequest> {
    @Query("select count(u) from UserRequest u where u.iin = ?1 and u.dbType.id = ?2")
    Long checkIin(String iin, Long dbTypeId);
    @Query("select count(u) from UserRequest u where u.firstName=?1 and u.middleName=?2 and u.lastName=?3 and u.dbType.id = ?4")
    Long checkFIO(String firstName, String middleName, String lastName, Long dbTypeId);
    @Query("select count(u) from UserRequest u where u.phone=?1 and u.dbType.id = ?2")
    Long checkPhone(String phone, Long dbTypeId);
}
