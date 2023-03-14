package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.*;
import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.AdminForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private SubjectRepository subjectRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Admin foundedAdmin = adminRepository.findByUsername(username);
            UserDetails securityAdmin = User.withUsername(username)
                    .password(foundedAdmin.getPassword())
                    .authorities(foundedAdmin.getRole().value)
                    .build();
            return securityAdmin;
        }catch (IllegalArgumentException e){
            log.error("msg={}", e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    public AdminDTO findById(Long id){
        Admin foundedAdmin = adminRepository.findById(id);
        return foundedAdmin.toDTO();
    }

    public AdminDTO findByUsername(String username){
        return adminRepository.findByUsername(username).toDTO();
    }

    public Map<String, Object> addWorkerByFile(Long id, MultipartFile file) throws IOException {
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
                    .detailedJob(DetailedJob.values()[Integer.parseInt(split[2])])
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
    public Map<String, Object> addMultiWorkers(Long id, List<Subject> subjects){
        Field foundedField = fieldRepository.findById(id);
        for (Subject subject : subjects) {
            subject.assignField(foundedField);
            Subject savedSubject = subjectRepository.save(subject);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subjects", subjects);
        return result;
    }
    public Map<String, Object> addSoleWorker(Long id, Subject subject){
        Field foundedField = fieldRepository.findById(id);
        subject.assignField(foundedField);
        Subject savedSubject = subjectRepository.save(subject);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subject", savedSubject);
        return result;
    }

    public AdminDTO addAdmin(AdminForm adminForm) {
        Admin admin = Admin.builder()
                .name(adminForm.getName())
                .username(adminForm.getUsername())
                .password(encoder.encode(adminForm.getPassword()))
                .role(Role.ROLE_ADMIN)
                .position(adminForm.getPosition())
                .build();

        return adminRepository.save(admin).toDTO();
    }

    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO){
        Admin founded = adminRepository.findById(id);
        return founded.update(adminDTO).toDTO();
    }

    public void deleteAdmin(Long id){
        adminRepository.delete(id);
    }

    public List<AdminDTO> findAll(){
        List<AdminDTO> founded = adminRepository.findAll()
                .stream().map(Admin::toDTO)
                .toList();
        return founded;
    }
}
