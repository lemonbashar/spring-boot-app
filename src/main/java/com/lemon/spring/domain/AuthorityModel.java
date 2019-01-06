package com.lemon.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authority")
public class AuthorityModel {
    public static final String CACHE = "AuthorityModel";

    @Id
    @NotNull
    @Column(unique = true,name = "AUTHORITY_NAME")
    private String name;

    public AuthorityModel() {
    }

    public AuthorityModel(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorityModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
