package com.lemon.spring.domain.internal;

import com.lemon.spring.annotation.AutoAudit;
import com.lemon.spring.enumeretion.NotificationType;
import com.lemon.spring.enumeretion.audit.AutoActive;
import com.lemon.spring.security.AuthoritiesConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "NOTIFICATION")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AutoAudit(autoActive = AutoActive.ACTIVE_IF_HAS_ROLE_ON_CREATE, activeInactiveRole = {AuthoritiesConstant.ROLE_ADMIN})
public class Notification extends AbstractAudit implements Serializable {
    public static final String CACHE = "NotificationCache";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTIFICATION_PK")
    @SequenceGenerator(name = "NOTIFICATION_PK", sequenceName = "NOTIFICATION_SEQ", allocationSize = 1)
    private BigInteger id;

    @Column(name = "NOTIFICATION")
    private String notification;

    @Column(name = "NOTIFICATION_TYPE")
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "USER")
    private User user;

    @Override
    protected BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
