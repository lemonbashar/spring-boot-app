package com.lemon.spring.domain;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Token-Store store the client side public IP Address that's why from a specific ip address only one user
 * Can access only one login-access
 * that's why we use their public IP
 * */
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
    private Date validateDate;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

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

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
