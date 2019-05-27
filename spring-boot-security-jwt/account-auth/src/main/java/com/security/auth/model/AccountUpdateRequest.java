package com.security.auth.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateRequest {

    private String name;

    @Builder
    public AccountUpdateRequest(String name) {
        this.name = name;
    }
}
