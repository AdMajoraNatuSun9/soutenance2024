package com.roadguardianbackend.roadguardianbackend.repository;

import com.roadguardianbackend.roadguardianbackend.entity.Role;
import com.roadguardianbackend.roadguardianbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);

//    User findByRole(Role role);
//    List<User> findByRolesContains(Role role);
}
