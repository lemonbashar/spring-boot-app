package com.lemon.spring.controller.rest;

import com.lemon.framework.data.JWToken;
import com.lemon.framework.data.LogoutInfo;
import com.lemon.framework.data.UserInfo;
import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.framework.springsecurity.filter.jwt.JWTFilter;
import com.lemon.spring.config.Constants;
import com.lemon.spring.domain.internal.User;
import com.lemon.spring.interfaces.WebController;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.service.account.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lemon.spring.config.Constants.GLOBAL_MESSAGE;
import static com.lemon.spring.interfaces.WebController.PRIVATE_REST;

@SuppressWarnings({"Duplicates", "WeakerAccess", "RedundantThrows", "unused"})
@RestController
@RequestMapping(PRIVATE_REST)
@Profile(value = {Constants.PROFILE_STATELESS, Constants.PROFILE_BOTH})
public class AccountControllerRest implements WebController<User, BigInteger> {
    public static final String BASE_PATH = "/account-controller";
    public static final String FULL_PATH = PRIVATE_REST + BASE_PATH;

    @Inject
    protected AccountService accountService;

    @Inject
    protected HbmCapture hbmCapture;

    @Inject
    protected UserRepository userRepository;

    @Inject
    protected UserService userService;

    private Logger log = LogManager.getLogger(AccountControllerRest.class);

    /*Account CRUD Related*/

    @Override
    @PostMapping(value = BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> save(@RequestBody User user) {
        accountService.register(user);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(GLOBAL_MESSAGE, "REGISTER_SUCCESS");
        return ResponseEntity.ok(objectMap);
    }

    @PreAuthorize("#user.username==principal.username || @auth.isAdmin()") /*Any User Can Update his Own identity or Admin can update all*/
    @Override
    @PutMapping(value = BASE_PATH)
    public ResponseEntity<Map<String, Object>> update(@RequestBody User user) {
        userService.updateUser(user);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(GLOBAL_MESSAGE, "UPDATE_SUCCESS");
        return ResponseEntity.ok(objectMap);
    }

    @PostAuthorize("returnObject.body.username==principal.username || @auth.isAdmin()") /*A User can get only his own identity and admin get all*/
    @Override
    @GetMapping(value = BASE_PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findOne(@PathVariable BigInteger id) {
        User user = hbmCapture.findOne(User.class, id);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("@auth.isAdmin()")
    @Override
    @GetMapping(value = BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return pageOf(page, BASE_PATH);
    }

    @PreAuthorize("@auth.isAdmin()")
    @Override
    @DeleteMapping(value = BASE_PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> delete(@PathVariable BigInteger id) {
        userRepository.deleteById(id);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(GLOBAL_MESSAGE, "DELETE_SUCCESS");
        return ResponseEntity.ok(objectMap);
    }

    /*Some Additional Admin Access*/

    @PreAuthorize("@auth.isAdmin()")
    @GetMapping(value = BASE_PATH + "/key/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String keyVal(@PathVariable String key) {
        if (key.equals("username")) return accountService.currentUsername();
        return "Not Found";
    }

    /*Authentication & Authorization Part*/

    @PostMapping(value = BASE_PATH + "/login-rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserInfo userInfo) throws IOException {
        boolean success = accountService.authenticate(userInfo) != null;
        Map<String, Object> map = new HashMap<>();
        map.put(GLOBAL_MESSAGE, "LOGIN_SUCCESS");
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = BASE_PATH + "/login-jwt", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWToken> loginJwt(@RequestBody UserInfo userInfo, HttpServletRequest httpServletRequest) {
        userInfo.setIpAddress(httpServletRequest.getRemoteAddr());
        String token = accountService.authenticate(userInfo).getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, JWTFilter.BEARER + token);
        return new ResponseEntity<>(new JWToken(token), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = BASE_PATH + "/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestBody LogoutInfo logoutInfo, HttpServletRequest httpServletRequest) {
        if (logoutInfo.getToken() == null || logoutInfo.getToken().isEmpty())
            logoutInfo.setToken(resolveToken(httpServletRequest));
        logoutInfo.setIpAddress(httpServletRequest.getRemoteAddr());
        accountService.logout(logoutInfo);
        Map<String, Object> map = new HashMap<>();
        map.put(GLOBAL_MESSAGE, "Logout is Success For Condition:" + logoutInfo.getLogoutRule().name().toLowerCase());
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = BASE_PATH + "/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest httpServletRequest) {
        String username = accountService.currentUsername();
        accountService.logout(null);
        Map<String, Object> map = new HashMap<>();
        map.put(GLOBAL_MESSAGE, "Logout is Success For:" + username.toLowerCase());
        return ResponseEntity.ok(map);
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        return StringUtils.hasText(bearer) && bearer.startsWith("Bearer ") ? bearer.substring("Bearer ".length()) : null;
    }
}
