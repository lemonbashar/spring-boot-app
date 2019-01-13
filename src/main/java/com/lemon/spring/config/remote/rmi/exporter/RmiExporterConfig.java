package com.lemon.spring.config.remote.rmi.exporter;

import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.config.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.rmi.RmiServiceExporter;

import javax.inject.Inject;
import java.math.BigInteger;

@Profile({Constants.PROFILE_REMOTE,Constants.PROFILE_RMI})
@Configuration
public class RmiExporterConfig {
    @Inject
    private ApplicationProperties properties;

    @Bean
    public RmiServiceExporter userServiceExport(AccountService<BigInteger> accountService) {
        Class<AccountService> service = AccountService.class;
        RmiServiceExporter serviceExporter=new RmiServiceExporter();
        serviceExporter.setServiceInterface(service);
        serviceExporter.setService(accountService);
        serviceExporter.setServiceName(service.getName());
        serviceExporter.setRegistryPort(45);
        return serviceExporter;
    }
}
