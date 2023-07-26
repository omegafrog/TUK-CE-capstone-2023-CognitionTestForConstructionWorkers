package com.tukorea.cogTest.domain;

import java.util.List;

public interface AdminRepository {
    Admin save(Admin admin);
    Admin findById(Long id);

    Admin findByUsername(String username);

    void delete(Long id);

    List<Admin> findAll();

    Admin findByFieldId(Long id);
}
