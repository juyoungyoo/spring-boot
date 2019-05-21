package com.security.api.foo;

import lombok.Data;
import lombok.Getter;

@Data
public class Foo {

    private long id;
    private String name;

    public Foo(long id,
               String name) {
        this.id = id;
        this.name = name;
    }
}
