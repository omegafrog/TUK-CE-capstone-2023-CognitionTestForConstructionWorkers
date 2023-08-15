package com.tukorea.cogTest.repository;



import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LogoutRepositoryImpl implements LogoutRepository {

    private static final Map<String, Date> storage = new ConcurrentHashMap<>();
    @Override
    public void save(String token, Date expirationTime) {
        if(storage.containsKey(token))
            storage.replace(token, expirationTime);
        else
            storage.put(token, expirationTime);
    }

    @Override
    public void deleteByToken(String token) throws NoSuchElementException{
        if(storage.containsKey(token))
            storage.remove(token);
        else
            throw new NoSuchElementException("그런 token을 가진 블랙리스트는 없습니다. : "+token);
    }

    @Override
    public boolean findByToken(String token) throws NoSuchElementException{
        Date date = storage.get(token);
        if(date == null) return false;
        return date.after(new Date());
    }

    @Scheduled(fixedDelay = 60000)
    public void invalidate(){
        log.info("invalidating logout blacklist");
        for(String key : storage.keySet()){
            if(storage.get(key).after(new Date())){
                storage.remove(key);
            }
        }
    }
}
