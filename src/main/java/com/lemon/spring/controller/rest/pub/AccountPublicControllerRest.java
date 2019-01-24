package com.lemon.spring.controller.rest.pub;

import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.controller.rest.AccountControllerRest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.lemon.spring.config.Constants.GLOBAL_MESSAGE;
import static com.lemon.spring.interfaces.WebController.PUBLIC_REST;

@RestController
@RequestMapping(value = PUBLIC_REST)
public class AccountPublicControllerRest {

    @Inject
    private AccountService accountService;

    @GetMapping(value = AccountControllerRest.BASE_PATH+"/password-recover/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> recoverPassword(@PathVariable String email) {
        accountService.passwordRecover(email);

        Map<String,Object>map=new HashMap<>();
        map.put(GLOBAL_MESSAGE,"We Sent A Recovery Code to Your E-Mail Address, Please Check It Out");

        return ResponseEntity.ok(map);
    }

    @GetMapping(value = AccountControllerRest.BASE_PATH+"/password-recover/{email}/{recoveryCode}/{newPassword}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> recoverPassword(@PathVariable String email,@PathVariable String recoveryCode,@PathVariable String newPassword ) {
        boolean isSuccess=accountService.passwordRecover(email,newPassword,recoveryCode);
        Map<String,Object> map=new HashMap<>();
        map.put(GLOBAL_MESSAGE,isSuccess?"Password Reset Is Successfully Completed":"Password Reset Is Not Successfully Done");
        return ResponseEntity.ok(map);
    }
}
