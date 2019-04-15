package com.lemon.spring.config.remote.hessian.exporter;

import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.config.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

import java.math.BigInteger;

@Profile({Constants.PROFILE_REMOTE, Constants.PROFILE_HESSIAN})
public class HessianExporterConfig {

    public RemoteExporter accountService(AccountService<BigInteger> accountService) {
        HessianServiceExporter serviceExporter = new HessianServiceExporter();
        serviceExporter.setServiceInterface(AccountService.class);
        serviceExporter.setService(accountService);
        return serviceExporter;
    }
}
