package com.ll.framework.ioc.util;

import com.ll.framework.ioc.util.sample.TestCar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClsUtilTest {
    @Test
    @DisplayName("ClsUtil.loadClass")
    void t1() {
        Class<TestCar> cls = ClsUtil.loadClass("com.ll.framework.ioc.util.sample.TestCar");

        assertThat(cls).isEqualTo(TestCar.class);
    }

    @Test
    @DisplayName("ClsUtil.construct")
    void t2() {
        TestCar testCar = ClsUtil.construct("com.ll.framework.ioc.util.sample.TestCar", new Object[]{"BMW", 1234});

        assertThat(testCar.getName()).isEqualTo("BMW");
        assertThat(testCar.getNumber()).isEqualTo(1234);
    }
}