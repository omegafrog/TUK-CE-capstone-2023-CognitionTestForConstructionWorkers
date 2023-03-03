package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data

public class AdminForm {
    String name;
    String username;
    String password;
    Role role;
    private String position;

    @Builder
    public AdminForm(String name, String username, String password, Role role, String position) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.position = position;
    }
}
