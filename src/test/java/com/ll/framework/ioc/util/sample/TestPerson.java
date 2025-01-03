package com.ll.framework.ioc.util.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestPerson {
    private String name;
    private int age;

    protected TestPerson() {
        this.name = "Paul";
        this.age = 9876;
    }
}
