package com.alikhansfamily.registrator.repository;

import com.alikhansfamily.registrator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    @Query("select count (u) from User u where u.username=?1")
    Long checkUser(String username);
}
