package com.techacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techacademy.entity.Authentication;
import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, String> {
    Optional<Authentication> findByCode(String code);
}
