package com.juyoung.springsecurityoauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer   // 인증을 하는 애
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // base 인증
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resource_id").stateless(false);
        // oauth token만으로 접근하려면 true
        // false : basic oauthtication 접근...
        // user password 따로 관리하는 경우
    }

    // resources 인증...
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
//                .requestMatchers().antMatchers("/users", "/oauth/users/**", "/oauth/clients/**", "/me")
//                    .and()
                    .authorizeRequests()
                .antMatchers("/users/**").authenticated();
    }
}
