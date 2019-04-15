package com.lemon.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    public static final String CACHE = "Authority";
    private static final long serialVersionUID=1L;

    @Id
    @NotNull
    @Column(unique = true, name = "AUTHORITY_NAME")
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
