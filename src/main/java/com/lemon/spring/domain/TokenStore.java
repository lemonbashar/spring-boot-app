package com.lemon.spring.domain;

import com.lemon.spring.annotation.AutoAudit;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Token-Store store the client side public IP Address that's why from a specific ip address only one user
 * Can access only one login-access
 * that's why we use their public IP
 */
@Entity
@Table(name = "TOKEN_STORE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AutoAudit
public class TokenStore extends AbstractAudit implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TOKEN_STORE_PK")
    @SequenceGenerator(name = "TOKEN_STORE_PK", sequenceName = "TOKEN_STORE_SEQ", allocationSize = 1)
    private BigInteger id;

    @Column(name = "JWT_TOKEN", length = 2048, unique = true)
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
