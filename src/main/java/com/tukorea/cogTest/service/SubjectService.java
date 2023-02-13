package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SubjectService implements UserDetailsService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Subject foundedSubject = subjectRepository.findByUsername(username);
            return User.withUsername(foundedSubject.getUsername())
                    .password(foundedSubject.getPassword())
                    .roles(foundedSubject.getRole().value)
                    .build();
        }catch (IllegalArgumentException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
