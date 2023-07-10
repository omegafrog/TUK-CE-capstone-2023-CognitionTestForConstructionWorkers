package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.User;

public interface LogoutRepository {
    User save(String token, User user);

    void deleteByToken(String token);
    User findByToken(String token);

}
