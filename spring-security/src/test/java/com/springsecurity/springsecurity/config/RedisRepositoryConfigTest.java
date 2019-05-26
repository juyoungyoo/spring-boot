package com.springsecurity.springsecurity.config;

import com.springsecurity.springsecurity.repository.AccessTokenRepository;
import com.springsecurity.springsecurity.security.AccessToken;
import com.springsecurity.springsecurity.security.JwtTokenProvider;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisRepositoryConfigTest {

    @Autowired
    private AccessTokenRepository accessTokenRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @After
    public void tearDown() throws Exception {
        accessTokenRepository.deleteAll();
    }

    @Test
    public void redis_registerd_accessToken() {
        long id = 1l;
        LocalDateTime refreshTime = LocalDateTime.of(2019, 5, 26, 0, 0);
        AccessToken accessToken = AccessToken.builder()
                .id(id)
                .rawToken("token")
                .refreshTime(refreshTime)
                .build();

        accessTokenRepository.save(accessToken);

        AccessToken savedAccount = accessTokenRepository.findById(id).get();
        assertThat(savedAccount.getId()).isEqualTo(id);
        assertThat(savedAccount.getRefreshTime()).isEqualTo(refreshTime);
    }

    @Test
    public void refresh_token() {
        AccessToken accessToken = saveAccessToken();
        long id = accessToken.getId();

        AccessToken savedAccessToken = accessTokenRepository.findById(id).get();
        String token = accessToken.getRawToken();
        LocalDateTime refreshTime = LocalDateTime.of(2019, 6, 1, 0, 0);
        savedAccessToken.refresh(token, refreshTime);
        accessTokenRepository.save(savedAccessToken);

        AccessToken refreshToken = accessTokenRepository.findById(id).get();
        assertThat(refreshToken.getId()).isEqualTo(id);
        assertThat(refreshToken.getRawToken()).isEqualTo(token);
        assertThat(refreshToken.getRefreshTime()).isEqualTo(refreshTime);
    }

    private AccessToken saveAccessToken(){
        long id = 1l;
        String token = "token";
        LocalDateTime refreshTime = LocalDateTime.of(2019, 5, 26, 0, 0);
        return accessTokenRepository.save(AccessToken.builder()
                .id(id)
                .rawToken(token)
                .refreshTime(refreshTime)
                .build());
    }
}