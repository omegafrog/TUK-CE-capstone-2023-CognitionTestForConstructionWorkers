package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.test.Pvt;
import com.tukorea.cogTest.domain.test.Twohand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
class TestResultTest {

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Test
    @Transactional
    void saveTest(){
        EntityManager em = emf.createEntityManager();
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        em.persist(field);
        Subject subject = Subject.builder()
                .name("testname")
                .age(10)
                .career(10)
                .detailedJob(DetailedJob.COMMON)
                .risk(Risk.HIGH_RISK)
                .remarks("")
                .field(field)
                .build();
        Subject savedSubject = subjectRepository.save(subject);
        TestResult result = TestResult.builder()
                .target(savedSubject)
                .build();
        TestResult savedResult = testResultRepository.save(result);
        TestResult foundedResult = testResultRepository.findById(savedResult.getId())
                .orElseThrow(() -> new IllegalArgumentException());
        Assertions.assertThat(savedResult).isEqualTo(foundedResult);
    }

    @Test
    @Transactional
    void updateTest(){
        EntityManager em = emf.createEntityManager();
        Field field = Field.builder()
                .name("field1")
                .numOfWorkers(0)
                .build();
        em.persist(field);
        Subject subject = Subject.builder()
                .name("testname")
                .age(10)
                .career(10)
                .detailedJob(DetailedJob.COMMON)
                .risk(Risk.HIGH_RISK)
                .remarks("")
                .field(field)
                .build();
        Subject savedSubject = subjectRepository.save(subject);
        TestResult result = TestResult.builder()
                .target(savedSubject)
                .twohandResult(
                        Twohand.builder()
                                .totalMeanDuration(123.4)
                                .totalMeanError(2.34)
                                .build()
                )
                .build();
        TestResult savedResult = testResultRepository.save(result);
        TestResult afterResult = TestResult.builder()
                .target(savedSubject)
                .twohandResult(Twohand.builder()
                        .totalMeanError(1.23)
                        .totalMeanDuration(1234.0)
                        .build())
                .pvtResult(new Pvt())
                .build();
        TestResult updatedResult = testResultRepository.update(savedResult.getId(), afterResult);
        Assertions.assertThat(savedResult).isEqualTo(updatedResult);
        Assertions.assertThat(updatedResult.getTwohandResult().getTotalMeanDuration())
                .isEqualTo(afterResult.getTwohandResult().getTotalMeanDuration());
    }
}