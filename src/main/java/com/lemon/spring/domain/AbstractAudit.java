package com.lemon.spring.domain;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractAudit {
    @ManyToOne
    @JoinColumn(name = "CREATE_BY")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "UPDATE_BY")
    private User updateBy;

    @Column(name = "CREATE_DATE")
    private LocalDate createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "ACTIVE_STATUS")
    private boolean activeStatus;

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

}
