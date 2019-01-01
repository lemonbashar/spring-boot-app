package com.lemon.spring.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "SPRING_USER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAudit {
    public static final String CACHE = "UserCache";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "spring_user_pk")
    @SequenceGenerator(name = "spring_user_pk",sequenceName = "spring_user_seq",allocationSize = 1)
    private BigInteger id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String fullName;

    @Column
    private boolean active;

    @Column
    private LocalDate activeDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_AUTHORITIES",
            joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY",referencedColumnName = "name")})
    private Set<Authority> authorities=new HashSet<>();

    public User() {
    }

    public User(BigInteger id) {
        this.id = id;
    }

    public User(BigInteger id, String username, String password) {
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(LocalDate activeDate) {
        this.activeDate = activeDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
