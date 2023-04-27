package com.tukorea.cogTest.service;

import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.SubjectForm;
import com.tukorea.cogTest.dto.TestResultDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface SubjectService extends UserDetailsService {
    List<TestResultDTO> findTestResult(Long subjectId);

    SubjectDTO findSubject(Long subjectId);

    List<SubjectDTO> findSubjectInField(Long fieldId);

    SubjectDTO findByUsername(String username);

    SubjectDTO update(Long id, SubjectForm subjectDTO);

    void delete(Long subjectId);
}
