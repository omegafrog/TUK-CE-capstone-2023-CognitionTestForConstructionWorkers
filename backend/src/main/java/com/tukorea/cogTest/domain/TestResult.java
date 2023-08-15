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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Subject target;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    private LocalDate date = LocalDate.now();

    @Embedded
    @AttributeOverride(name = "failedCount", column = @Column(name = "TWOHAND_FAILED_COUNT"))
    @AttributeOverride(name = "isPassed", column = @Column(name = "TWOHAND_ISPASSED"))
    @AttributeOverride(name = "minResponseTime", column = @Column(name = "TWOHAND_MINRESPONSETIME"))
    private Twohand twoHandResult;

    @Embedded
    @AttributeOverride(name = "isPassed", column = @Column(name = "DIGITSPAN_ISPASSED"))
    private DigitSpan digitSpanResult;

    @Embedded
    @AttributeOverride(name = "isPassed", column = @Column(name = "CONVEYOR_ISPASSED"))
    @AttributeOverride(name = "elapsedTime", column = @Column(name = "CONVEYOR_ELAPSEDTIME"))
    private Conveyor conveyorResult;

    @Embedded
    @AttributeOverride(name = "isPassed", column = @Column(name = "MAZE_ISPASSED"))
    @AttributeOverride(name = "collisionCount", column = @Column(name = "MAZE_COLLISIONCOUNT"))
    private Maze mazeResult;
    @Embedded
    @AttributeOverride(name = "isPassed", column = @Column(name = "DECISIONMAKING_ISPASSED"))
    @AttributeOverride(name = "minResponseTime", column = @Column(name = "DECISIONMAKING_MINRESPONSETIME"))
    @AttributeOverride(name = "missedGo", column = @Column(name = "DECISIONMAKING_MISSEDGO"))
    @AttributeOverride(name = "missClickedNoGo", column = @Column(name = "DECISIONMAKING_MISSCLICKEDNOGO"))
    private DecisionMaking decisionMakingResult;

    @Builder
    public TestResult(Long id, Subject target,  Twohand twoHandResult, DigitSpan digitSpanResult, Conveyor conveyorResult, Maze mazeResult, DecisionMaking decisionMakingResult) {
        this.id = id;
        this.target = target;
        this.twoHandResult = twoHandResult;
        this.digitSpanResult = digitSpanResult;
        this.conveyorResult = conveyorResult;
        this.mazeResult = mazeResult;
        this.decisionMakingResult = decisionMakingResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestResult item = (TestResult) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }


    public TestResult update(TestResult item) {
        this.target = item.getTarget();
        this.twoHandResult = item.getTwoHandResult();
        this.digitSpanResult = item.getDigitSpanResult();
        return this;
    }

    /**
     * 테스트 결과에 테스트를 진행한 피험자를 할당한다.
     *
     * @param target : 테스트를 진행한 피험자 객체
     */
    public void assignTarget(Subject target) {
        this.target = target;
    }

    public TestResultDTO toDTO() {
        return TestResultDTO.builder().id(id).target(target.toDTO()).date(date).twoHandResult(twoHandResult).digitSpanResult(digitSpanResult).conveyorResult(conveyorResult).mazeResult(mazeResult).decisionMakingResult(decisionMakingResult).build();
    }
}
