package com.lemon.spring.domain;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "TOKEN_STORE")
public class TokenStore extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "token_store_pk")
    @SequenceGenerator(name = "token_store_pk",sequenceName = "token_store_seq",allocationSize = 1)
    private BigInteger id;

    @Column(name = "JWT_TOKEN",length = 2048,unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "SPRING_USER_TOKEN")
    private User user;

    @Column(name = "VALIDATE_DATE")
    private LocalDate validateDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(LocalDate validateDate) {
        this.validateDate = validateDate;
    }
}
