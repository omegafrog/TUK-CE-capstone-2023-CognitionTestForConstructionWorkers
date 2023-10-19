package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import com.tukorea.cogTest.domain.NotPassedCount;
import com.tukorea.cogTest.domain.NotPassedRepository;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.repository.JpaNotPassedCountRepository;
import com.tukorea.cogTest.repository.NotPassedRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotPassedCountService {

    private final NotPassedRepository notPassedCountRepository;
    private final FieldRepository fieldRepository;

    /**
     * test result를 저장할 때 notPassedCount를 업데이트하는 함수
     * @param byUsername
     */

    public void updateNotPassedCount(SubjectDTO byUsername) {
        List<NotPassedCount> byFieldId = notPassedCountRepository.findByField_id(byUsername.getField().getId());
        Field byId = fieldRepository.findById(byUsername.getField().getId());
        if(byFieldId.isEmpty()){
            NotPassedCount notPassedCount = new NotPassedCount(byId);
            notPassedCount.increaseCount();
            notPassedCountRepository.save(notPassedCount);
            return;
        }
        NotPassedCount todayNotPassedCount = byFieldId.stream().filter(count -> count.getDate().equals(LocalDate.now())).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cannot find not passed count entity."));
        todayNotPassedCount.increaseCount();

    }
}
