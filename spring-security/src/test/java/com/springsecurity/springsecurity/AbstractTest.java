package com.springsecurity.springsecurity;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.domain.AuthProviders;
import com.springsecurity.springsecurity.domain.RoleType;
import com.springsecurity.springsecurity.security.SecurityUser;

import java.util.Arrays;
import java.util.HashSet;

public class AbstractTest {

    public static String USER_NAME = "juyoung";
    public static String USER_EMAIL = "juyoung@gmail.com";
    public static String USER_PASSWORD = "pass";

    public static Account getAccount(){
        Account account = Account.builder()
                .username(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .role(RoleType.USER)
                .mailYn(false)
                .providers(new HashSet<>(Arrays.asList(AuthProviders.local)))
                .build();

        account.setId(1L);
        return account;
    }

    public static SecurityUser getSecurityUser(){
        return new SecurityUser(getAccount());
    }
}
