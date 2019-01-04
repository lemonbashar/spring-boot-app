package com.lemon.spring.config.security;

import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.framework.properties.constants.PropertiesConstants;
import com.lemon.framework.springsecurity.auth.AuthenticationService;
import com.lemon.framework.springsecurity.auth.complete.CompleteUserInfoAuthenticationService;
import com.lemon.framework.springsecurity.jwt.JWTAuthConfigAdapter;
import com.lemon.framework.springsecurity.jwt.auth.JWTAuthenticationService;
import com.lemon.framework.springsecurity.jwt.auth.complete.CompleteUserInfoJwtAuthenticationTokenService;
import com.lemon.framework.springsecurity.jwt.provider.AuthenticationTokenTokenProvider;
import com.lemon.framework.springsecurity.jwt.provider.TokenProvider;
import com.lemon.spring.component.security.CompleteTokenStoreBridge;
import com.lemon.spring.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.inject.Inject;

import static com.lemon.spring.security.AuthoritiesConstant.ROLE_ADMIN;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] LIST_OF_COOKIES_TO_DELETE_WHEN_LOG_OUT = {"LOGIN_ID_COOKIE","JSESSIONID"};

    @Inject
    private ApplicationProperties applicationProperties;

    @Autowired(required = false)
    private CompleteTokenStoreBridge completeTokenStoreBridge;


    @Autowired(required = false)
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserDetailsService customUserDetailsService;

    @Inject
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Inject
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Inject
    private LogoutSuccessHandler logoutSuccessHandler;

    @Inject
    private LogoutHandler logoutHandler;

    @Autowired(required = false)
    private SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> securityConfigurerAdapter;



    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
            .inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("lemon").password("123456").roles("ADMIN");*/

        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
          if(applicationProperties.settings.applicationType.equalsIgnoreCase(PropertiesConstants.APPLICATION_TYPE_STATELESS))
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();/*Make Spring-Boot Application Stateless*/
          http.csrf().disable()
                /*Login Related*/
          .formLogin()
                .loginPage("/web/account-controller/login")
                .failureForwardUrl("/web/account-controller/login?error")
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/api/account-controller/login")
                .successForwardUrl("/home")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll().and()
          .logout().logoutUrl("/web/account-controller/logout").addLogoutHandler(logoutHandler)
                .clearAuthentication(true)
                .deleteCookies(LIST_OF_COOKIES_TO_DELETE_WHEN_LOG_OUT)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/web/account-controller/login")
                .logoutSuccessHandler(logoutSuccessHandler).and()
                /*Url Invoker Interceptor*/
          .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/","/home*").permitAll()/* Disable This Line if You Want The Login Page At-Startup*/
                .mvcMatchers(HttpMethod.POST,"/api/account-controller/login*").permitAll()/*Rest-API Permission for Login Purposes*/
                .mvcMatchers(HttpMethod.POST,"/api/account-controller/login-rest*").permitAll()/*Rest-API Permission for Login Purposes*/
                .mvcMatchers(HttpMethod.POST,"/api/account-controller/login-jwt*").permitAll()/*Rest-API Permission for Login Purposes*/
                .mvcMatchers(HttpMethod.GET,"/web/account-controller/register*").permitAll()/*Register API Permit For Registration*/
                .mvcMatchers(HttpMethod.POST,"/web/account-controller*").permitAll()/*Register API Permit For Registration*/
                .mvcMatchers(HttpMethod.POST,"/api/account-controller*").permitAll()/*When Click to Register, All User Data need to Store on Database, and for this reason it has been permitted */
                .mvcMatchers(HttpMethod.GET,"/api/account-controller/key/*").hasAnyAuthority(ROLE_ADMIN)
                .anyRequest().authenticated();
                //.antMatchers("/api/**","/web/**").authenticated();
        if(applicationProperties.settings.applicationType.equalsIgnoreCase(PropertiesConstants.APPLICATION_TYPE_STATELESS) || applicationProperties.settings.applicationType.equalsIgnoreCase(PropertiesConstants.APPLICATION_TYPE_BOTH))http.apply(securityConfigurerAdapter);
    }

    @Profile(value = {Constants.PROFILE_STATELESS,Constants.PROFILE_BOTH})
    @Bean(initMethod = "init")
    public TokenProvider tokenProvider() {
        return new AuthenticationTokenTokenProvider(applicationProperties,completeTokenStoreBridge);
    }

    @Profile(value = {Constants.PROFILE_STATELESS,Constants.PROFILE_BOTH})
    @Bean
    public JWTAuthConfigAdapter jwtAuthConfigAdapter() {
        return new JWTAuthConfigAdapter(tokenProvider);
    }

    @Profile(value = {Constants.PROFILE_STATELESS,Constants.PROFILE_BOTH})
    @Bean
    public JWTAuthenticationService jwtAuthenticationService() {
        return new CompleteUserInfoJwtAuthenticationTokenService(authenticationManager, (AuthenticationTokenTokenProvider) tokenProvider,customUserDetailsService,passwordEncoder);
    }

    @Bean
    public AuthenticationService authenticationService() {
        return new CompleteUserInfoAuthenticationService(authenticationManager,customUserDetailsService,passwordEncoder);
    }
}
