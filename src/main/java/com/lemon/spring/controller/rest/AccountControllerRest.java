package com.lemon.spring.controller.rest;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.spring.data.UserInfo;
import com.lemon.spring.domain.User;
import com.lemon.spring.interfaces.WebController;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.service.account.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountControllerRest implements WebController<User> {
    public static final String BASE_PATH="/account-controller";

    @Inject
    private AccountService accountService;
    //private final UserRepository userRepository;

    @Inject
    private HbmCapture hbmCapture;

    @Inject
    private UserRepository userRepository;

    private Logger log= LogManager.getLogger(AccountControllerRest.class);


    @Override
    @PostMapping(value = BASE_PATH,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> save(@RequestBody User user) {
        accountService.register(user);
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("REGISTER_SUCCESS",true);
        objectMap.put("OBJECT",user);
        return ResponseEntity.ok(objectMap);
    }

    @Override
    @PutMapping(value = BASE_PATH)
    public ResponseEntity<Map<String, Object>> update(@RequestBody User entity) {
        return null;
    }

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

    @Override
    @DeleteMapping(value = BASE_PATH+"/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> delete(Long id) {
        return null;
    }

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
        System.out.println("<><><><><> A Login Processor For Rest Control");
        boolean success=accountService.login(userInfo.getUsername(),userInfo.getPassword());
        Map<String,Object> map=new HashMap<>();
        map.put("success",success);
        return ResponseEntity.ok(map);
    }
}
