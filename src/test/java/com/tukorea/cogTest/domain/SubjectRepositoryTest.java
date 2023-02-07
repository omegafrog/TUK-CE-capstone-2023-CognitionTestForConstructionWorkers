package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.dto.SubjectDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @PersistenceUnit
    public EntityManagerFactory emf;

    @Test
    @Transactional
    void saveNFindTest(){
        EntityManager em = emf.createEntityManager();
        Field field = Field.builder()
                .name("testField1")
                .numOfWorkers(0)
                .build();
        em.persist(field);
        Subject subject = Subject.builder()
                .age(10)
                .career(10)
                .field(field)
                .name("testWorker")
                .detailedJob(DetailedJob.COMMON)
                .risk(Risk.HIGH_RISK)
                .build();
        Subject savedEntity = subjectRepository.save(subject);
        Subject foundedEntity = subjectRepository.findById(savedEntity.getId()).orElseThrow();
        System.out.println("savedEntity = " + savedEntity.getId());
        System.out.println("foundedEn = " + foundedEntity.getId());
        Assertions.assertThat(savedEntity).isEqualTo(foundedEntity);
    }

    @Test
    @Transactional
    void updateSubjectTest(){
        EntityManager em = emf.createEntityManager();
        Field field = Field.builder()
                .name("testField1")
                .numOfWorkers(0)
                .build();
        em.persist(field);
        Subject subject = Subject.builder()
                .age(10)
                .career(10)
                .field(field)
                .name("testWorker")
                .detailedJob(DetailedJob.COMMON)
                .risk(Risk.HIGH_RISK)
                .build();
        Subject savedEntity = subjectRepository.save(subject);
        SubjectDTO updateDTO = Subject.builder()
                .age(11)
                .career(11)
                .field(field)
                .name("changedTestWorker")
                .detailedJob(DetailedJob.SPECIAL)
                .risk(Risk.MIDEUM_RISK)
                .build().toDTO();
        Subject updatedSubject = savedEntity.update(updateDTO);
        Assertions.assertThat(updatedSubject.getName()).isEqualTo(updateDTO.getName());
    }
}