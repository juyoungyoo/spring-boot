package com.security.auth.config;

import com.security.auth.common.AppProperties;
import com.security.auth.domain.RoleType;
import com.security.auth.model.SignUpRequest;
import com.security.auth.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {
            @Autowired
            CustomUserDetailsService customUserDetailsService;
            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                setFixtureAccount("admin", appProperties.getAdminId(), appProperties.getAdminPassword(), RoleType.ADMIN);
                setFixtureAccount("user", appProperties.getUserId(), appProperties.getUserPassword(), RoleType.USER);
            }

            private void setFixtureAccount(String name,
                                           String email,
                                           String password,
                                           RoleType roleType) {
                SignUpRequest account = SignUpRequest.builder()
                        .name(name)
                        .email(email)
                        .password(password)
                        .roleType(roleType)
                        .emailVerified(true)
                        .build();
                try {
                    customUserDetailsService.loadUserByUsername(email);
                } catch (Exception e) {
                    customUserDetailsService.signUp(account);
                }
                log.info("init data success : " + account.getName());
            }
        };
    }
}
