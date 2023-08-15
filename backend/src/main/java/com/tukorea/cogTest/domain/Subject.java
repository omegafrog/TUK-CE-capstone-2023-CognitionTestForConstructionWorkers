package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.Sex;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.UpdateSubjectDto;
import jakarta.persistence.*;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "subject")

@NoArgsConstructor
@Getter
public class Subject extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String username;
    String password;

    Role role;

    private String phoneNum;
    private Sex sex;
    private Integer age;
    private Integer career;
    private String remarks;
    private Risk risk=Risk.NORMAL;
    private LocalDate lastTestedDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="field_id")
    private Field field;

    @OneToMany(mappedBy = "target", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TestResult> testResults;

    public void setLastTestedDate(LocalDate testedDate){
        this.lastTestedDate = testedDate;
    }


    @Builder
    public Subject(
            Long id,
            String name,
            String username,
            String password,
            Role role,
            Integer age,
            Integer career,
            String remarks,
            Risk risk,
            Field field,
            String phoneNum, Sex sex) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.age = age;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.field = field;
        this.phoneNum = phoneNum;
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Subject item = (Subject) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    public Subject update(UpdateSubjectDto subject){
        this.name = subject.getName();
        this.age = subject.getAge();
        this.career = subject.getCareer();
        this.field = subject.getField();
        this.remarks = subject.getRemarks();
        this.password = subject.getPhoneNum();
        return this;
    }

    public void changeRiskLevel(Risk risk){
        this.risk = risk;
    }

    public void assignField(Field field){
        this.field = field;
    }

    public SubjectDTO toDTO(){
        return SubjectDTO.builder()
                .id(this.id)
                .age(this.age)
                .name(this.name)
                .username(this.getUsername())
                .password(this.getPassword())
                .career(this.career)
                .field(this.field.toDTO())
                .remarks(this.remarks)
                .risk(this.risk)
                .phoneNum(phoneNum)
                .sex(sex)
                .lastTestedDate(lastTestedDate)
                .build();
    }

    public void encodePassword(PasswordEncoder encoder){
        this.password = encoder.encode(password);
    }
}
