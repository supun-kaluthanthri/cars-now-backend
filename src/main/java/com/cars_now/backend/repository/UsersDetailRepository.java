package com.cars_now.backend.repository;

import com.cars_now.backend.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDetailRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String name);

    Optional<Users> findByEmail(String email);
}
