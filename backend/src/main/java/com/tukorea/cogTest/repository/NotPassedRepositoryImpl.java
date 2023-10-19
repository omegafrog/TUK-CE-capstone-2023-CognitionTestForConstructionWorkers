package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.NotPassedCount;
import com.tukorea.cogTest.domain.NotPassedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NotPassedRepositoryImpl implements NotPassedRepository {

    private final JpaNotPassedCountRepository repository;

    @Override
    public List<NotPassedCount> findByField_id(Long fieldId) {
        return repository.findByField_id(fieldId);

    }

    @Override
    public NotPassedCount save(NotPassedCount entity) {
        return repository.save(entity);
    }
}
