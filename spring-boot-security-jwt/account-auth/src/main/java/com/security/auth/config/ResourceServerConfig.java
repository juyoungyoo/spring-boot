package com.security.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

// 어떤 외부 요청이 resource에 접근할 때 인증이 필요하다면 oAuth token service에 확인하는.. 유효한지 확인하는 역할
// 인증정보가 있는지 없는지 확인하고 접근제한을 한다
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers( "/api/users/**", "/api/users").authenticated()
                .antMatchers(HttpMethod.GET, "/configuration/security").permitAll()
                .anyRequest().authenticated();
    }
}
