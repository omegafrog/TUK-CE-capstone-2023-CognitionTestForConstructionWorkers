package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSimpleAdminRepository extends JpaRepository<Admin, Long> {

}
