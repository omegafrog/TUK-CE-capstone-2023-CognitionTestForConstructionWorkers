package com.tukorea.cogTest.security.domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityAdminRepository extends JpaRepository<SecurityAdmin, Long> {
    SecurityAdmin findByUsername(String username);
}
