package com.lemon.spring.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.inject.Inject;

import static com.lemon.spring.security.AuthoritiesConstant.ROLE_ADMIN;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] LIST_OF_COOKIES_TO_DELETE_WHEN_LOG_OUT = {"LOGIN_ID_COOKIE"};
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


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
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
        http
          .csrf().disable()
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
                .mvcMatchers(HttpMethod.GET,"/web/account-controller/register*").permitAll()/*Register API Permit For Registration*/
                .mvcMatchers(HttpMethod.POST,"/api/account-controller*").permitAll()/*When Click to Register, All User Data need to Store on Database, and for this reason it has been permitted */
                .mvcMatchers(HttpMethod.GET,"/api/account-controller/key/*").hasAnyAuthority(ROLE_ADMIN)
                .anyRequest().authenticated();
                //.antMatchers("/api/**","/web/**").authenticated();
    }
}
