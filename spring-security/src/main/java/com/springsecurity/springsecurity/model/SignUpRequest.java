package com.springsecurity.springsecurity.model;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.domain.AuthProviders;
import com.springsecurity.springsecurity.domain.RoleType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SignUpRequest {

    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;
    @NotEmpty(message = "password is wrong")
    private String password;
    @NotEmpty(message = "username is wrong")
    private String username;
    @NotEmpty(message = "role is wrong")
    private RoleType role;
    private boolean mailYn;
    private String providerId;
    private AuthProviders provider = AuthProviders.local;

    @Builder
    public SignUpRequest(@NotEmpty String email,
                         @NotEmpty String password,
                         @NotEmpty String username,
                         @NotEmpty RoleType role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.mailYn = true;
        this.provider = AuthProviders.local;
    }

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .mailYn(mailYn)
                .status(true)
                .providers(new HashSet<>(Arrays.asList(provider)))
                .build();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
