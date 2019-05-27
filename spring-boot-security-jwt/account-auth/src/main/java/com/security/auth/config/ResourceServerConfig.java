package com.security.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
//@Profile("mvc")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/employee")
                .hasRole("ADMIN")
                .anyRequest().authenticated()
                .antMatchers("/login").permitAll()
//                .antMatchers("/oauth/token/revokeById/**").permitAll()
//                .antMatchers("/tokens/**").permitAll()
                .and()
                .formLogin()
                .and()
                .csrf().disable()
                .httpBasic()
        ;
    }
}
