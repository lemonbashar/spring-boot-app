package com.lemon.spring.config.security;

import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.framework.properties.constants.PropertiesConstants;
import com.lemon.framework.springsecurity.jwt.JWTAuthConfigAdapter;
import com.lemon.framework.springsecurity.jwt.JwtAuthManager;
import com.lemon.framework.springsecurity.jwt.provider.TokenProvider;
import com.lemon.framework.springsecurity.jwt.provider.TokenStoreTokenProvider;
import com.lemon.framework.springsecurity.session.SessionAuthManager;
import com.lemon.spring.component.security.TokenStoreBridge;
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

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] LIST_OF_COOKIES_TO_DELETE_WHEN_LOG_OUT = {"LOGIN_ID_COOKIE","JSESSIONID"};

    @Inject
    private ApplicationProperties applicationProperties;

    @Autowired(required = false)
    private TokenStoreBridge tokenStoreBridge;


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
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
          if(applicationProperties.settings.applicationType.equalsIgnoreCase(PropertiesConstants.APPLICATION_TYPE_STATELESS)) /*That Means For Stateful & Both session generate not turn off*/
              http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);/*Make Spring-Boot Application Stateless*/
                /*Login Related*/
          if(!applicationProperties.settings.applicationType.equalsIgnoreCase(PropertiesConstants.APPLICATION_TYPE_STATELESS))  /*That means if only application is enabled stateless then it not handle form-login otherwise handle form-login*/
              http.formLogin()
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
                    .logoutSuccessHandler(logoutSuccessHandler);
                    /*Url Invoker Interceptor*/
              http.authorizeRequests()
                    .mvcMatchers(HttpMethod.GET,"/","/home*").permitAll()/* Disable This Line if You Want The Login Page At-Startup*/
                    .mvcMatchers(HttpMethod.POST,"/api/account-controller/login*").permitAll()/*Rest-API Permission for Login Purposes*/
                    .mvcMatchers(HttpMethod.POST,"/api/account-controller/login-rest*").permitAll()/*Rest-API Permission for Login Purposes*/
                    .mvcMatchers(HttpMethod.POST,"/api/account-controller/login-jwt*").permitAll()/*Rest-API Permission for Login Purposes*/
                    .mvcMatchers(HttpMethod.GET,"/web/account-controller/save-entry*").permitAll()/*Register API Permit For Registration*/
                    .mvcMatchers(HttpMethod.POST,"/web/account-controller*").permitAll()/*Register API Permit For Registration*/
                    .mvcMatchers(HttpMethod.POST,"/api/account-controller*").permitAll()/*When Click to Register, All User Data need to Store on Database, and for this reason it has been permitted */
                    .mvcMatchers(HttpMethod.GET,"/api/account-controller/key/*").hasAnyAuthority(ROLE_ADMIN)
                    .mvcMatchers("/web/public/**").permitAll()
                    .mvcMatchers("/api/public/**").permitAll()

                     /*Url Mapping For HTTP-INVOKER-REMOTE-SERVICE*/
                    .mvcMatchers("/accountService.service*").permitAll()
                     /*Url Mapping For HTTP-INVOKER-REMOTE-SERVICE*/
                .anyRequest().authenticated();
                //.antMatchers("/api/**","/web/**").authenticated();
        if(!applicationProperties.settings.applicationType.equalsIgnoreCase(PropertiesConstants.APPLICATION_TYPE_STATEFUL))http.apply(securityConfigurerAdapter); /*That means if only application is enabled stateful then it not handle token otherwise handle token*/
    }

    @Profile(value = {Constants.PROFILE_STATELESS,Constants.PROFILE_BOTH})
    @Bean(initMethod = "init")
    public TokenProvider tokenProvider() {
        return new TokenStoreTokenProvider(applicationProperties, tokenStoreBridge);
    }

    @Profile(value = {Constants.PROFILE_STATELESS,Constants.PROFILE_BOTH})
    @Bean
    public JWTAuthConfigAdapter jwtAuthConfigAdapter() {
        return new JWTAuthConfigAdapter(tokenProvider);
    }

    @Profile(value = {Constants.PROFILE_STATELESS,Constants.PROFILE_BOTH})
    @Bean
    public JwtAuthManager jwtAuthenticationService() {
        return new JwtAuthManager(authenticationManager, tokenProvider);
    }

    @Profile(value = {Constants.PROFILE_STATEFUL,Constants.PROFILE_BOTH})
    @Bean
    public SessionAuthManager authenticationService() {
        return new SessionAuthManager(authenticationManager);
    }
}
