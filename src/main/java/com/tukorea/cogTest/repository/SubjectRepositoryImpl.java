package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {

    public final JpaSimpleSubjectRepository simpleSubjectRepository;
    @Override
    public Subject update(Long id, Subject updateArgument) {
        Subject foundedSubject = simpleSubjectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("그런 피험자는 없습니다." + id));
        return foundedSubject.update(updateArgument.toDTO());
    }

    @Override
    public Subject save(Subject subject) {
        subject.getField().appendWorkerNum();
        return simpleSubjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return simpleSubjectRepository.findById(id);
    }
}
