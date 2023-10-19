package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Admin;
import com.tukorea.cogTest.domain.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {

    private final JpaSimpleAdminRepository simpleAdminRepository;

    public AdminRepositoryImpl(JpaSimpleAdminRepository simpleAdminRepository) {
        this.simpleAdminRepository = simpleAdminRepository;
    }

    @Override
    public Admin save(Admin admin) throws RuntimeException{
        return simpleAdminRepository.save(admin);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        Admin foundedAdmin = findById(id);
        simpleAdminRepository.delete(foundedAdmin);

    }

    @Override
    public List<Admin> findAll() {
        return simpleAdminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) throws RuntimeException {
        return simpleAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such founded admin. " + id));
    }

    @Override
    public Admin findByFieldId(Long id) {
        return simpleAdminRepository.findByField_id(id)
                .orElseThrow(() -> new IllegalArgumentException("No such founded admin that has field " + id + "."));
    }

    @Override
    public Admin findByUsername(String username) {
        return simpleAdminRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No such founded admin. " + username));
    }
}
