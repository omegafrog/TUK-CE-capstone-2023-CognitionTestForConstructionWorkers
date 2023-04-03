package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.test.*;
import com.tukorea.cogTest.dto.TestResultDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="target_id")
    private Subject target;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    private LocalDate date = LocalDate.now();

    @Column(name = "TWO_HAND")
    @Embedded
    private Twohand twoHandResult;
    @Column(name="PVT")
    @Embedded
    private Pvt pvtResult;
    @Column(name = "CRANE")
    @Embedded
    private Crane craneResult;
    @Column(name = "MAZE")
    @Embedded
    private Maze mazeResult;
    @Column(name = "TOVA")
    @Embedded
    private Tova tovaResult;

    @Builder
    public TestResult(
            Long id,
            Subject target,
            LocalDate date,
            Twohand twoHandResult,
            Pvt pvtResult,
            Crane craneResult,
            Maze mazeResult,
            Tova tovaResult) {
        this.id = id;
        this.target = target;
        this.date = date;
        this.twoHandResult = twoHandResult;
        this.pvtResult = pvtResult;
        this.craneResult = craneResult;
        this.mazeResult = mazeResult;
        this.tovaResult = tovaResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TestResult item = (TestResult) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }


    public TestResult update(TestResult item){
        this.target = item.getTarget();
        this.twoHandResult = item.getTwoHandResult();
        this.pvtResult = item.getPvtResult();
        return this;
    }

    /**
     * 테스트 결과에 테스트를 진행한 피험자를 할당한다.
     * @param target : 테스트를 진행한 피험자 객체
     */
    public void assignTarget(Subject target){
        this.target = target;
    }

    public TestResultDTO toDTO(){
        return TestResultDTO.builder()
                .id(id)
                .target(target)
                .date(date)
                .twoHandResult(twoHandResult)
                .pvtResult(pvtResult)
                .craneResult(craneResult)
                .mazeResult(mazeResult)
                .tovaResult(tovaResult)
                .build();
    }
}
