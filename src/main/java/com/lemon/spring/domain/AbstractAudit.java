package com.lemon.spring.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractAudit {

    @Column
    private LocalDate createDate;

    @Column
    private LocalDate updateDate;

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

    @PrePersist
    public void prePersist() {
        this.createDate=LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate=LocalDate.now();
    }
}
