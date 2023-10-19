package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.*;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.AdminForm;
import com.tukorea.cogTest.dto.SubjectForm;
import com.tukorea.cogTest.dto.UpdateAdminDto;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;
    private final FieldRepository fieldRepository;
    private final SubjectRepository subjectRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, RuntimeException {
        try {
            Admin foundedAdmin = adminRepository.findByUsername(username);
            return User.withUsername(username)
                    .password(foundedAdmin.getPassword())
                    .roles(foundedAdmin.getRole().value)
                    .build();
        } catch (IllegalArgumentException e) {
            log.error("msg={}", e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public AdminDTO findById(Long id) throws RuntimeException {
        Admin foundedAdmin = adminRepository.findById(id);
        return foundedAdmin.toDTO();
    }

    public AdminDTO findByUsername(String username) throws RuntimeException{
        return adminRepository.findByUsername(username).toDTO();
    }

    public Map<String, Object> addWorkerByFile(Long id, MultipartFile file) throws RuntimeException, IOException {
        Field foundedField = fieldRepository.findById(id);

        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<Subject> subjectList = new ArrayList<>();
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String[] split = line.split(",");
            Subject subject = Subject.builder()
                    .name(split[0])
                    .age(Integer.parseInt(split[1]))
                    .career(Integer.parseInt(split[3]))
                    .remarks(split[4])
                    .risk(Risk.values()[Integer.parseInt(split[5])])
                    .field(foundedField)
                    .build();
            Subject savedSubject = subjectRepository.save(subject);
            subjectList.add(savedSubject);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subjects", subjectList);
        return result;
    }

    public Map<String, Object> addMultiWorkers(Long id, List<SubjectForm> subjects) throws RuntimeException {
        Field foundedField = fieldRepository.findById(id);
        for (SubjectForm subjectForm : subjects) {
            Subject subject = Subject.builder()
                    .name(subjectForm.getName())
                    .username(subjectForm.getUsername())
                    .password(encoder.encode(subjectForm.getPassword()))
                    .role(Role.USER)
                    .risk(Risk.NORMAL)
                    .age(subjectForm.getAge())
                    .career(subjectForm.getCareer())
                    .field(foundedField)
                    .build();
            subjectRepository.save(subject);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subjects", subjects);
        return result;
    }

    public Map<String, Object> addSoleWorker(Long fieldId, SubjectForm subjectForm)
            throws RuntimeException {
        Field foundedField = fieldRepository.findById(fieldId);
        Subject subject = Subject.builder()
                .name(subjectForm.getName())
                .age(subjectForm.getAge())
                .username(subjectForm.getUsername())
                .password(encoder.encode(subjectForm.getPassword()))
                .role(Role.USER)
                .risk(Risk.NORMAL)
                .field(foundedField)
                .build();
        Subject savedSubject = subjectRepository.save(subject);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subject", savedSubject.toDTO());
        return result;
    }

    public AdminDTO addAdmin(AdminForm adminForm) throws RuntimeException {
        Field selectedField = fieldRepository.findById(adminForm.getFieldId());
        selectedField.appendWorkerNum();
        Admin admin = Admin.builder()
                .name(adminForm.getName())
                .username(adminForm.getUsername())
                .password(encoder.encode(adminForm.getPassword()))
                .role(Role.ADMIN)
                .field(selectedField)
                .position(adminForm.getPosition())
                .build();
        AdminDTO saved = adminRepository.save(admin).toDTO();
        return saved;
    }

    public AdminDTO updateAdmin(Long id, AdminForm adminForm) throws RuntimeException {
        Admin founded = adminRepository.findById(id);
        Field byId = fieldRepository.findById(adminForm.getFieldId());
        UpdateAdminDto build = UpdateAdminDto.builder()
                .name(adminForm.getName())
                .password(adminForm.getPassword())
                .field(byId)
                .position(adminForm.getPosition())
                .username(adminForm.getUsername())
                .build();
        Admin updated = founded.update(build);
        return adminRepository.save(updated).toDTO();
    }

    public void deleteAdmin(Long id) throws RuntimeException {
        adminRepository.delete(id);
    }

    public List<AdminDTO> findAll() throws RuntimeException {
        return adminRepository.findAll()
                .stream().map(Admin::toDTO)
                .toList();
    }

    public AdminDTO findByField_id(Long id) throws RuntimeException {
        return adminRepository.findByFieldId(id).toDTO();
    }
}
