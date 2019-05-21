package com.security.auth.security;


import com.security.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("john").password(passwordEncoder.encode("123")).roles("USER").and()
                .withUser("tom").password(passwordEncoder.encode("111")).roles("ADMIN").and()
                .withUser("user1").password(passwordEncoder.encode("pass")).roles("USER").and()
                .withUser("admin").password(passwordEncoder.encode("nimda")).roles("ADMIN");
    }// @formatter:on

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .requestMatchers()
                    .antMatchers("/login", "/oauth/authorize")
                        .and()
    //                .antMatchers("/oauth/token/revokeById/**").permitAll()
    //                .antMatchers("/tokens/**").permitAll()
                    .authorizeRequests()
                        .anyRequest().authenticated()
                .and()
                    .formLogin().permitAll()
                .and()
                    .csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder);
//    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().antMatchers("/h2-console/**");
    }
}