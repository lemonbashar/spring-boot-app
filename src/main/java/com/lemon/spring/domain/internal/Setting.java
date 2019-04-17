package com.lemon.spring.domain.internal;

import com.lemon.spring.annotation.AutoAudit;
import com.lemon.spring.enumeretion.audit.AutoActive;
import com.lemon.spring.security.AuthoritiesConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "SETTING")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AutoAudit(autoActive = AutoActive.ACTIVE_IF_HAS_ROLE_ON_CREATE, activeInactiveRole = {AuthoritiesConstant.ROLE_ADMIN})
public class Setting extends AbstractAudit implements Serializable {
    public static final String CACHE = "SettingCache";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SETTING_PK")
    @SequenceGenerator(name = "SETTING_PK", sequenceName = "SETTING_SEQ", allocationSize = 1)
    private BigInteger id;

    @Column(name = "SETTING_KEY",unique = true,nullable = false)
    private String settingKey;

    @Column(name = "SETTING_VALUE")
    private String settingValue;

    @Column(name = "DATA_TYPE")
    private String dataType;

    @Override
    protected BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
