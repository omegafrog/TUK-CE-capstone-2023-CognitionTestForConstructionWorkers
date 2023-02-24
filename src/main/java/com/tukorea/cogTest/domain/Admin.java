package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.AdminDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Admin {
    @Id
    @GeneratedValue
    Long id;

    String name;
    String username;
    String password;
    Role role;
    private String position;


    @OneToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Builder
    public Admin(Long id, String name, String username, String password, Role role, String position, Field field) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.position = position;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Admin item = (Admin) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    public Admin update(Admin admin){
        this.name = admin.getName();
        this.field = admin.getField();
        this.position = admin.getPosition();
        this.role = admin.getRole();
        return this;
    }

    public AdminDTO toDTO(){
        return AdminDTO.builder()
                .id(id)
                .name(name)
                .field(field)
                .username(username)
                .password(password)
                .position(position)
                .role(role)
                .build();
    }
}
