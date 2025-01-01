package com.ll.framework.ioc.util.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestCar {
    private String name;
    private int number;

    public TestCar() {
        this.name = "BenZ";
        this.number = 9876;
    }
}
