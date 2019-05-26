package com.springsecurity.springsecurity.domain;

public enum RoleType {
    ADMIN, USER;

    @Override
    public String toString() {
        return "ROLE_" + this.name();
    }
}
