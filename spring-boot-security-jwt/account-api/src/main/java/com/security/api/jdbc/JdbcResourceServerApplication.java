package com.security.api.jdbc;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@SpringBootApplication
@EnableResourceServer
public class JdbcResourceServerApplication {

    public static void main(String[] args){
        SpringApplication.run(JdbcResourceServerApplication.class);
    }

    @Configuration
    static class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Value("${spring.datasource.driver-class-name}")
        private String driverClassName;
        @Value("${spring.datasource.url}")
        private String url;
        @Value("${spring.datasource.password}")
        private String password;
        @Value("${spring.datasource.username}")
        private String userName;

        @Override
        public void configure(final HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .authorizeRequests().anyRequest().permitAll();
        }

        @Override
        public void configure(final ResourceServerSecurityConfigurer config) {
            config.tokenServices(tokenServices());
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore());
            return defaultTokenServices;
        }

        // JDBC token store configuration
        @Bean
        public DataSource dataSource() {
            final DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            return dataSource;
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource());
        }

    }

}
