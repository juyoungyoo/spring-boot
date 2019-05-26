package com.springsecurity.springsecurity.config;

import com.springsecurity.springsecurity.security.jwt.JwtAuthenticationFilter;
import com.springsecurity.springsecurity.security.jwt.JwtAuthenticationProvider;
import com.springsecurity.springsecurity.security.jwt.SkipPathRequestMatcher;
import com.springsecurity.springsecurity.security.login.LoginAuthenticationProvider;
import com.springsecurity.springsecurity.security.login.LoginFailureHandler;
import com.springsecurity.springsecurity.security.login.LoginProcessFilter;
import com.springsecurity.springsecurity.security.login.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTHENTICATION_HEADER_NAME = "Autholinirization";
    private static final String AUTHENTICATION_URL = "/sign-in";

    @Autowired
    private LoginAuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    private LoginFailureHandler failureHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(jwtAuthenticationProvider)
                .authenticationProvider(authenticationProvider)
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
                .authorizeRequests()
                .antMatchers("/sign-up").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
        ;
    }

    @Bean
    protected LoginProcessFilter loginFilter() throws Exception {
        LoginProcessFilter loginProcessFilter = new LoginProcessFilter(new AntPathRequestMatcher("/sign-in"));
        loginProcessFilter.setAuthenticationManager(authenticationManager());
        loginProcessFilter.setAuthenticationSuccessHandler(successHandler);
        loginProcessFilter.setAuthenticationFailureHandler(failureHandler);
        return loginProcessFilter;

    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        SkipPathRequestMatcher skipPathRequestMatcher = new SkipPathRequestMatcher(Arrays.asList("/sign-up", "/sign-in", "/"));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipPathRequestMatcher);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
