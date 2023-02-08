package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.test.Pvt;
import com.tukorea.cogTest.domain.test.Twohand;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="subject")
    private Subject target;

    @Column(name = "TWO_HAND")
    @Embedded
    private Twohand twohandResult;
    @Column(name="PVT")
    @Embedded
    private Pvt pvtResult;

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
    @Builder
    public TestResult(Long id, Subject target, Twohand twohandResult, Pvt pvtResult) {
        this.id = id;
        this.target = target;
        this.twohandResult = twohandResult;
        this.pvtResult = pvtResult;
    }




    public TestResult update(TestResult item){
        this.target = item.getTarget();
        this.twohandResult = item.getTwohandResult();
        this.pvtResult = item.getPvtResult();
        return this;
    }
}
