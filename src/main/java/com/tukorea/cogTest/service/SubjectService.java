package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectService implements UserDetailsService{
    private final SubjectRepository subjectRepository;
    private final TestResultRepository testResultRepository;

    /**
     * 피험자의 id를 받아서 피험자를 검색한 후 그 피험자의 모든 테스트 내용을 가져온다.
     * @param subjectId : 피험자의 id
     * @return List&lt;TestResult&gt; : 테스트 결과 객체 리스트
     * @throws RuntimeException : 피험자의 id에 해당하는 피험자를 찾지 못했을 경우 IllegalArgumentException을 throw한다.
     */
    public List<TestResult> findTestResult(Long subjectId) throws RuntimeException{
        Subject foundedSubject = subjectRepository.findById(subjectId);
        return testResultRepository.findByUserId(foundedSubject.getId());
    }

    /**
     * 피험자의 id에 해당하는 피험자 객체를 반환한다.
     * @param subjectId : 피험자의 id
     * @return Subject : 피험자 객체
     * @throws RuntimeException : 피험자의 id에 해당하는 피험자를 찾지 못했을 경우 IllegalArgumentException을 throw한다.
     */
    public Subject findSubject(Long subjectId) throws RuntimeException{
        return subjectRepository.findById(subjectId);
    }
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         try {
             Subject foundedSubject = subjectRepository.findByUsername(username);
             return User.withUsername(foundedSubject.getUsername())
                     .password(foundedSubject.getPassword())
                     .roles(foundedSubject.getRole().value)
                     .build();
         } catch (IllegalArgumentException e) {
             throw new UsernameNotFoundException(e.getMessage());
         }
     }
}
