package com.lemon.spring.component.audit;

import com.lemon.spring.annotation.AutoAudit;
import com.lemon.spring.domain.AbstractAudit;

public interface AuditAware {

    void aware(AbstractAudit audit, AutoAudit autoAudit);

    void awareUpdate(AbstractAudit audit,AutoAudit autoAudit);

    void awareCreate(AbstractAudit audit,AutoAudit autoAudit);
}
