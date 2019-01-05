package com.lemon.spring.controller.rest;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.springsecurity.jwt.JWTFilter;
import com.lemon.framework.web.data.JWToken;
import com.lemon.framework.web.data.LogoutInfo;
import com.lemon.framework.web.data.UserInfo;
import com.lemon.spring.component.audit.AuditAware;
import com.lemon.spring.config.Constants;
import com.lemon.spring.domain.User;
import com.lemon.spring.interfaces.WebController;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.security.AuthoritiesConstant;
import com.lemon.spring.service.account.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"Duplicates", "WeakerAccess", "RedundantThrows", "unused"})
@RestController
@RequestMapping("/api")
public class AccountControllerRest implements WebController<User> {
    public static final String BASE_PATH="/account-controller";

    @Inject
    private AccountService accountService;

    @Inject
    private HbmCapture hbmCapture;

    @Inject
    private UserRepository userRepository;

    private Logger log = LogManager.getLogger(AccountControllerRest.class);


    @Override
    @PostMapping(value = BASE_PATH,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> save(@RequestBody User user) {
        accountService.register(user);
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("REGISTER_SUCCESS",true);
        log.debug("Successfully Registered...");
        return ResponseEntity.ok(objectMap);
    }

    @Override
    @PutMapping(value = BASE_PATH)
    public ResponseEntity<Map<String, Object>> update(@RequestBody User entity) {
        userRepository.save(entity);
        return null;
    }

    @PostAuthorize("returnObject.body.username==principal.username")
    @Override
    @GetMapping(value = BASE_PATH+"/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        log.info("Finding One User by Id:"+id);
        return ResponseEntity.ok(hbmCapture.findOne(User.class,id));
    }

    @Cacheable(value = User.CACHE)
    @Override
    @GetMapping(value = BASE_PATH,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {
        log.info("Finding All Users");
        return ResponseEntity.ok(hbmCapture.getAll(User.class));
    }

    @Secured(AuthoritiesConstant.ROLE_ADMIN)
    @Override
    @DeleteMapping(value = BASE_PATH+"/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        return null;
    }

    @Secured(AuthoritiesConstant.ROLE_ADMIN)
    @GetMapping(value = BASE_PATH+"/key/{key}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String keyVal(@PathVariable String key) {
        if(key.equals("username")) return accountService.currentUsername();
        return "Not Found";
    }

    /*@PostMapping(value = BASE_PATH+"/login")
    public void login(HttpServletResponse response, @RequestParam(name = "username",required = true) String username, @RequestParam(name = "password",required = true) String password) throws IOException {
        User user=accountService.login(username,password);
        if(user!=null) {
            response.sendRedirect("/web/account-controller/profile/"+username);
        }
        else response.sendRedirect("/web/account-controller/register");
    }*/


    @PostMapping(value = BASE_PATH+"/login-rest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> login(@RequestBody UserInfo userInfo) throws IOException {
        boolean success=accountService.login(userInfo.getUsername(),userInfo.getPassword());
        Map<String,Object> map=new HashMap<>();
        map.put("success",success);
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = BASE_PATH+"/login-jwt",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWToken> loginJwt(@RequestBody UserInfo userInfo, HttpServletRequest httpServletRequest) {
        userInfo.setIpAddress(httpServletRequest.getRemoteAddr());
        String token=accountService.authenticate(userInfo).getToken();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER,JWTFilter.BEARER+token);
        return new ResponseEntity<>(new JWToken(token),httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = BASE_PATH+"/logout")
    public ResponseEntity<Map<String,Object>> logout(@RequestBody LogoutInfo logoutInfo, HttpServletRequest httpServletRequest) {
        accountService.logout(logoutInfo,httpServletRequest);
        Map<String ,Object> map=new HashMap<>();
        map.put(Constants.GLOBAL_MESSAGE,"Logout is Success For Condition:"+logoutInfo.getLogoutRule().name());
        return ResponseEntity.ok(map);
    }


    @GetMapping(value = BASE_PATH+"/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public void loginAfter(Model  model) throws IOException {
        log.debug("<><><><><> A Login Processor For Rest Control");
    }
}
