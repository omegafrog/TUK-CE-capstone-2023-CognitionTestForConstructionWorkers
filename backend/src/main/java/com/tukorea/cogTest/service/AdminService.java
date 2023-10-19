package com.tukorea.cogTest.service;

import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.AdminForm;
import com.tukorea.cogTest.dto.SubjectForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface AdminService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    AdminDTO findById(Long id);
    AdminDTO findByUsername(String username);
    Map<String, Object> addMultiWorkers(Long id, List<SubjectForm> subjects);
    Map<String, Object> addSoleWorker( Long fieldId, SubjectForm subjectForm);
    AdminDTO addAdmin(AdminForm adminForm);
    AdminDTO updateAdmin(Long id, AdminForm adminForm);
    void deleteAdmin(Long id);
    List<AdminDTO> findAll();
    AdminDTO findByField_id(Long id);

}
