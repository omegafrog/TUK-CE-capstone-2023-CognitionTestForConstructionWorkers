package com.tukorea.cogTest.repository;


import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TestResultRepositoryImpl implements TestResultRepository {

    private final JpaSimpleTestResultRepository simpleTestResultRepository;
    private final JpaSimpleSubjectRepository simpleSubjectRepository;
    @Override
    public TestResult save(TestResult item) {
        Long userId = item.getTarget().getId();
        if(userId == null){
            throw new NullPointerException("유저 id가 null입니다.");
        }
        simpleSubjectRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("검사결과를 할당할 유저가 잘못되었습니다." + userId));
        return simpleTestResultRepository.save(item);
    }

    @Override
    public TestResult update(Long id, TestResult item) throws RuntimeException{
        TestResult foundedResult = findById(id);
        return foundedResult.update(item);
    }

    @Override
    public TestResult findById(Long id) throws RuntimeException{
        return simpleTestResultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("그런 테스트 결과는 없습니다." + id));
    }
}
