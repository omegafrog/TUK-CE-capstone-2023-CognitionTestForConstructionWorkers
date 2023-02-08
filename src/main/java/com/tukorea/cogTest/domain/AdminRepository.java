package com.tukorea.cogTest.domain;

import java.util.Optional;

public interface AdminRepository {
    Admin save(Admin admin);
    Admin findById(Long id);

    Admin update(Long id, Admin admin);
    void delete(Long id);

}