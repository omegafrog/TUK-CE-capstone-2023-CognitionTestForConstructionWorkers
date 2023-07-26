package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.enums.Role;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
public class UpdateAdminDto {
    private String name;
    private String username;
    private String password;
    private Field field;
    private String position;
    private Role role;

    @Builder
    public UpdateAdminDto(String name, String username, String password, Field field, String position, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.field = field;
        this.position = position;
        this.role = role;
    }
}
