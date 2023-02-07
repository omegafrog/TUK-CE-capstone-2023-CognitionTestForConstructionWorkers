package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {

    public final JpaSimpleSubjectRepository simpleSubjectRepository;
    @Override
    public Subject update(Long id, Subject updateArgument) {
        Subject foundedSubject = simpleSubjectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("그런 피험자는 없습니다." + id));
        return foundedSubject.update(updateArgument.toDTO());
    }
}
