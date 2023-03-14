package com.tukorea.cogTest.security.domain;

import com.tukorea.cogTest.domain.Admin;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Entity
public class SecurityAdmin extends User {


    @Id
    @GeneratedValue
    Long id;

    @Column(unique = true)
    String username;

    @Column
    String password;
    @Transient
    Admin details;

    public SecurityAdmin(){
        super(null, null, AuthorityUtils.createAuthorityList());
    }

    public SecurityAdmin(String username, String password, Collection<? extends GrantedAuthority> authorities, Admin admin){
        super(username, password, authorities);
        this.details = admin;
    }

}

