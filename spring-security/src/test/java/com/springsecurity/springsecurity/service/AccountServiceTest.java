package com.springsecurity.springsecurity.service;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.domain.RoleType;
import com.springsecurity.springsecurity.model.SignInRequest;
import com.springsecurity.springsecurity.model.SignUpRequest;
import com.springsecurity.springsecurity.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    private String email = "juyoung@email.com";
    private String userName = "juyoung";
    private String password = "pass";

    @Test
    public void signUp_success() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email(email)
                .password(password)
                .username(userName)
                .role(RoleType.USER)
                .build();

        Account expected = signUpRequest.toEntity();
        expected.setId(1L);

        given(accountRepository.save(any())).willReturn(expected);

        Account result = accountService.signUp(signUpRequest);

        assertThat(result.getUsername()).isEqualTo(userName);
    }

    @Test
    public void signIn_Succeess() {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();

        Account account = Account.builder()
                .username(email)
                .email(email)
                .password(password)
                .mailYn(false)
                .role(RoleType.USER)
                .build();

        given(accountRepository.findByEmail(email)).willReturn(Optional.of(account));
        Account result = accountService.signIn(signInRequest);

        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void signIn_Fail() {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();

        given(accountRepository.findByEmail(email)).willReturn(Optional.empty());
        accountService.signIn(signInRequest);
    }
}