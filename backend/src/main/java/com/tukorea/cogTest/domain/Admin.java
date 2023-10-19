package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.UpdateAdminDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String name;
    @NotNull
    Role role;
    private String position;


    @ManyToOne
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

    public Admin update(UpdateAdminDto admin){
        this.name = (admin.getName()==null)?this.name:admin.getName();
        this.username = (admin.getUsername()==null)?this.username:admin.getUsername();
        this.password = (admin.getPassword()==null)?this.password:admin.getPassword();
        this.field = (admin.getField()==null)?this.field:admin.getField();
        this.position = (admin.getPosition()==null)?this.position:admin.getPosition();
        this.role = (admin.getRole()==null)?this.role:admin.getRole();
        return this;
    }

    public AdminDTO toDTO(){
        return AdminDTO.builder()
                .id(id)
                .name(name)
                .username(username)
                .password(password)
                .field(field.toDTO())
                .position(position)
                .role(role)
                .build();
    }
}
