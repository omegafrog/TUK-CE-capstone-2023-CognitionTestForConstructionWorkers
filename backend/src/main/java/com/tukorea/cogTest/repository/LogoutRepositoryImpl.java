package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.User;


import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class LogoutRepositoryImpl implements LogoutRepository {

    private static final Map<String, User> storage = new ConcurrentHashMap<>();
    @Override
    public User save(String token, User user) {
        if(storage.containsKey(token))
            storage.replace(token, user);
        else
            storage.put(token, user);
        return user;
    }

    @Override
    public void deleteByToken(String token) throws NoSuchElementException{
        if(storage.containsKey(token))
            storage.remove(token);
        else
            throw new NoSuchElementException("그런 token을 가진 블랙리스트는 없습니다. : "+token);
    }

    @Override
    public User findByToken(String token) throws NoSuchElementException{
        User user = storage.get(token);
        if(user == null)
            throw new NoSuchElementException("그런 token을 가진 블랙리스트는 없습니다. : "+token);
        return user;
    }
}
