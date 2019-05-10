package com.security.api;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

@SpringBootApplication
@EnableResourceServer
public class ResourceApplication {
    public static void main(String[] args){
        SpringApplication.run(ResourceApplication.class);
    }

    @Configuration
    class resourceServer extends ResourceServerConfigurerAdapter {

        @Autowired
        CustomAccessTokenConverter customAccessTokenConverter;

        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(accessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setAccessTokenConverter(customAccessTokenConverter);
//            converter.setSigningKey("123"); // sign key
            Resource resource = new ClassPathResource("public.txt");
            String publicKey = null;
            try {
                publicKey = IOUtils.toString(resource.getInputStream());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
            converter.setVerifierKey(publicKey);
            return converter;
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore());
            defaultTokenServices.setSupportRefreshToken(true);
            return defaultTokenServices;
        }
        // base 인증
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenServices(tokenServices());
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
}
