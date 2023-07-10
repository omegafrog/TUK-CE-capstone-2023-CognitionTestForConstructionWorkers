package com.tukorea.cogTest.repository;


import org.springframework.security.core.Authentication;

import java.util.Date;

public interface LogoutRepository {
    void save(String token, Date expriateionTime);

    void deleteByToken(String token);
    boolean findByToken(String token);

}
