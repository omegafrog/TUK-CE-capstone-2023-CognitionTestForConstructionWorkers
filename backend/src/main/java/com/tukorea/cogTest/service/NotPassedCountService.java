package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import com.tukorea.cogTest.domain.NotPassedCount;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.repository.NotPassedCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotPassedCountService {

    private final NotPassedCountRepository notPassedCountRepository;
    private final FieldRepository fieldRepository;


    public void updateNotPassedCount(SubjectDTO byUsername) {
        Optional<NotPassedCount> byFieldId = notPassedCountRepository.findByField_id(byUsername.getField().getId());
        Field byId = fieldRepository.findById(byUsername.getField().getId());
        if (byFieldId.isEmpty()) {
            NotPassedCount notPassedCount = new NotPassedCount(byId);
            notPassedCount.increaseCount();
            notPassedCountRepository.save(notPassedCount);
        } else {
            byFieldId.get().increaseCount();
        }
    }
}
