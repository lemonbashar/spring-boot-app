package com.lemon.spring.domain;

import com.lemon.spring.annotation.AutoAudit;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unused", "unchecked"})
@Entity
@Table(name = "SPRING_USER")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AutoAudit
public class UserModel extends AbstractAudit{
    public static final String CACHE = "UserCache";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "SPRING_USER_PK")
    @SequenceGenerator(name = "SPRING_USER_PK",sequenceName = "SPRING_USER_SEQ",allocationSize = 1)
    private BigInteger id;

    @Column(unique = true,name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(unique = true,name = "E_MAIL")
    private String email;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "ACTIVE_DATE")
    private LocalDate activeDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_AUTHORITIES",
            joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY",referencedColumnName = "AUTHORITY_NAME")})
    private Set<AuthorityModel> authorities=new HashSet<>();

    public UserModel() {
    }

    public UserModel(BigInteger id) {
        this.id = id;
    }

    public UserModel(BigInteger id, String username, String password) {
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

    public void setAuthorities(Set<AuthorityModel> authorities) {
        this.authorities = authorities;
    }

    public Set<AuthorityModel> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
