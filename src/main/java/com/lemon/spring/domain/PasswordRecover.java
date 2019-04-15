package com.lemon.spring.domain;

import com.lemon.spring.annotation.AutoAudit;
import com.lemon.spring.enumeretion.audit.AutoActive;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PASSWORD_RECOVER")
@AutoAudit(autoActive = AutoActive.ACTIVE_ON_CREATE)
public class PasswordRecover extends AbstractAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PASSWORD_RECOVER_PK")
    @SequenceGenerator(name = "PASSWORD_RECOVER_PK", sequenceName = "PASSWORD_RECOVER_SEQ", allocationSize = 2, initialValue = 3242)
    private BigInteger id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "RECOVERY_CODE", length = 256, nullable = false)
    private String recoveryCode;

    @Column(nullable = false, name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "LAST_ACCESS_DATE")
    private LocalDate lastAccessDate;

    @Column(name = "WRONG_ACCESS_COUNT")
    private Long wrongAccessCount;

    @Override
    protected BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDate getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDate lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public Long getWrongAccessCount() {
        return wrongAccessCount;
    }

    public void setWrongAccessCount(Long wrongAccessCount) {
        this.wrongAccessCount = wrongAccessCount;
    }

    @Transient
    public void increaseAccessCodeCount() {
        this.wrongAccessCount++;
    }
}
