package com.security.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    @Builder
    public SignInRequest(@NotEmpty String email,
                         @NotEmpty String password) {
        this.email = email;
        this.password = password;
    }
}
