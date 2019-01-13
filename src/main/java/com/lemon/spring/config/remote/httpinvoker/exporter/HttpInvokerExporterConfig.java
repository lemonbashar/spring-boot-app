package com.lemon.spring.config.remote.httpinvoker.exporter;

import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.config.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import java.math.BigInteger;

@Configuration
@Profile({Constants.PROFILE_REMOTE,Constants.PROFILE_HTTP_INVOKER})
public class HttpInvokerExporterConfig {

    @Bean(name = "/accountService.service")
    public HttpInvokerServiceExporter accountService(AccountService<BigInteger> accountService) {
        HttpInvokerServiceExporter serviceExporter=new HttpInvokerServiceExporter();
        serviceExporter.setService(accountService);
        serviceExporter.setServiceInterface(AccountService.class);
        return serviceExporter;

    }
}
