package com.lemon.spring.component.audit;

import com.lemon.spring.domain.AbstractAudit;

public interface AuditAware {

    void aware(AbstractAudit audit);

    void awareUpdate(AbstractAudit audit);

    void awareCreate(AbstractAudit audit);
}
