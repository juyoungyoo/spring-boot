package com.springsecurity.springsecurity.repository;

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
public class AccessTokenRepositoryTest {

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
        String rawToken = "testtoken";
        AccessToken accessToken = registedAccessToken(id, rawToken);
        //then
        AccessToken savedAccount = accessTokenRepository.findById(id).get();
        assertThat(savedAccount.getId()).isEqualTo(id);
    }

    public AccessToken registedAccessToken(long id, String token){
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        AccessToken accessToken = AccessToken.builder()
                .id(id)
                .rawToken(token)
                .refreshTime(refreshTime)
                .build();
        return accessTokenRepository.save(accessToken);
    }

    @Test
    public void findById() {
        //given
        long id = 1l;
        registedAccessToken(id, "testtoken");
        accessTokenRepository.findById(id);

        //then
        AccessToken savedAccount = accessTokenRepository.findById(id).get();
        assertThat(savedAccount.getId()).isEqualTo(id);
    }

    @Test
    public void refresh_token() {
        //given
        Long id = 1l;
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        String token = "token";
        accessTokenRepository.save(AccessToken.builder()
                .id(id)
                .rawToken(token)
                .refreshTime(refreshTime)
                .build());

        //when
        AccessToken savedAccessToken = accessTokenRepository.findById(id).get();
        savedAccessToken.refresh(token, LocalDateTime.of(2018, 6, 1, 0, 0));
        accessTokenRepository.save(savedAccessToken);

        //then
        AccessToken refreshToken = accessTokenRepository.findById(id).get();
        assertThat(refreshToken.getId()).isEqualTo(id);
        assertThat(refreshToken.getRawToken()).isEqualTo(token);
        assertThat(refreshToken.getRefreshTime()).isEqualTo(LocalDateTime.of(2018, 6, 1, 0, 0));
    }
}