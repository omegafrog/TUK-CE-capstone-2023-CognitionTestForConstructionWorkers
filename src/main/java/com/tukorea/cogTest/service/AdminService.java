package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Admin;
import com.tukorea.cogTest.domain.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Admin foundedAdmin = adminRepository.findByUsername(username);

            return User.withUsername(foundedAdmin.getUsername())
                    .password(foundedAdmin.getPassword())
                    .authorities(foundedAdmin.getRole().value)
                    .build();
        }catch (IllegalArgumentException e){
            log.error("msg={}", e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
