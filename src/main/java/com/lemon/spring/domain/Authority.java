package com.lemon.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authority")
public class Authority {
    public static final String CACHE = "Authority";

    @Id
    @NotNull
    @Column(unique = true,name = "AUTHORITY_NAME")
    private String name;

    public Authority() {
    }

    public Authority(@NotNull String name) {
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
        return name;
    }
}
