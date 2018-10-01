package com.lemon.spring.config.security;

import com.lemon.spring.config.security.encoder.SimplePasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.inject.Inject;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    private SimplePasswordEncoder passwordEncoder;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("lemon").password("123456").roles("ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http/*Login Related*/
          .formLogin()
                .loginPage("/web/account-controller/login")
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/api/account-controller/login")
                .permitAll().and().csrf().disable()

                /*Url Invoker Interceptor*/
                .authorizeRequests().anyRequest().permitAll();
                //.antMatchers("/api/**","/web/**").permitAll();
    }
}
