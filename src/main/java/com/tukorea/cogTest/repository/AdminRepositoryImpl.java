package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Admin;
import com.tukorea.cogTest.domain.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {
    private final JpaSimpleAdminRepository simpleAdminRepository;

    @Override
    public Admin save(Admin admin) {
        return simpleAdminRepository.save(admin);
    }

    @Override
    public void delete(Long id) throws RuntimeException{
        Admin foundedAdmin = findById(id);
        simpleAdminRepository.delete(foundedAdmin);

    }

    @Override
    public Admin findById(Long id) throws RuntimeException{
        return simpleAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("그런 관리자는 없습니다." + id));
    }

    @Override
    public Admin update(Long id, Admin admin) {
        Admin foundedAdmin = simpleAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("그런 admin은 없습니다." + id));
        return foundedAdmin.update(admin);
    }
}
