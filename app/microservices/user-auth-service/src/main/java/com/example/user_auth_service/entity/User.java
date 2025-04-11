package com.example.user_auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "`user`")
public class User extends BaseModel {
    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}