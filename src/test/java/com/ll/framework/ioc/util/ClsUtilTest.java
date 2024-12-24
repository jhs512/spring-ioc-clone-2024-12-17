package com.ll.framework.ioc.util;

import com.ll.framework.ioc.util.sample.TestCar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

public class ClsUtilTest {
    @Test
    @DisplayName("ClsUtil.loadClass")
    void t1() {
        Class<TestCar> cls = ClsUtil.loadClass("com.ll.framework.ioc.util.sample.TestCar");

        assertThat(cls).isEqualTo(TestCar.class);
    }

    @Test
    @DisplayName("ClsUtil.construct with clsPath")
    void t2() {
        TestCar testCar = ClsUtil.construct("com.ll.framework.ioc.util.sample.TestCar", new Object[]{"BMW", 1234});
        // 위 코드는 아래와 같은 의미이다.
        // new TestCar("BMW", 1234);

        assertThat(testCar.getName()).isEqualTo("BMW");
        assertThat(testCar.getNumber()).isEqualTo(1234);
    }

    @Test
    @DisplayName("ClsUtil.construct with cls")
    void t3() {
        TestCar testCar = ClsUtil.construct(TestCar.class, new Object[]{"BMW", 1234});
        // 위 코드는 아래와 같은 의미이다.
        // new TestCar("BMW", 1234);

        assertThat(testCar.getName()).isEqualTo("BMW");
        assertThat(testCar.getNumber()).isEqualTo(1234);
    }

    @Test
    @DisplayName("ClsUtil.getParameters with clsPath")
    void t4() {
        Parameter[] parameters = ClsUtil.getParameters("com.ll.framework.ioc.util.sample.TestCar", new Object[]{"BMW", 1234});

        assertThat(parameters[0].getType()).isEqualTo(String.class);
        assertThat(parameters[0].getName()).isEqualTo("name");

        assertThat(parameters[1].getType()).isEqualTo(int.class);
        assertThat(parameters[1].getName()).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameters with cls")
    void t5() {
        Parameter[] parameters = ClsUtil.getParameters(TestCar.class, new Object[]{"BMW", 1234});

        assertThat(parameters[0].getType()).isEqualTo(String.class);
        assertThat(parameters[0].getName()).isEqualTo("name");

        assertThat(parameters[1].getType()).isEqualTo(int.class);
        assertThat(parameters[1].getName()).isEqualTo("number");
    }
}