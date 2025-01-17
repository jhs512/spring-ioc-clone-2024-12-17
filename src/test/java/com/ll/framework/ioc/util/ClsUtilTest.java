package com.ll.framework.ioc.util;

import com.ll.domain.testPost.testPost.repository.TestPostRepository;
import com.ll.domain.testPost.testPost.service.TestFacadePostService;
import com.ll.domain.testPost.testPost.service.TestPostService;
import com.ll.framework.ioc.annotations.Component;
import com.ll.framework.ioc.util.sample.TestCar;
import com.ll.framework.ioc.util.sample.TestPerson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.Map;

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
    @DisplayName("ClsUtil.getParameters with clsPath, parameters")
    void t4() {
        Parameter[] parameters = ClsUtil.getParameters("com.ll.framework.ioc.util.sample.TestCar", new Object[]{"BMW", 1234});

        assertThat(parameters[0].getType()).isEqualTo(String.class);
        assertThat(parameters[0].getName()).isEqualTo("name");

        assertThat(parameters[1].getType()).isEqualTo(int.class);
        assertThat(parameters[1].getName()).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameters with clsPath, parameterTypes")
    void t5() {
        Parameter[] parameters = ClsUtil.getParameters("com.ll.framework.ioc.util.sample.TestCar", new Class[]{String.class, int.class});

        assertThat(parameters[0].getType()).isEqualTo(String.class);
        assertThat(parameters[0].getName()).isEqualTo("name");

        assertThat(parameters[1].getType()).isEqualTo(int.class);
        assertThat(parameters[1].getName()).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameters with cls, parameters")
    void t6() {
        Parameter[] parameters = ClsUtil.getParameters(TestCar.class, new Object[]{"BMW", 1234});

        assertThat(parameters[0].getType()).isEqualTo(String.class);
        assertThat(parameters[0].getName()).isEqualTo("name");

        assertThat(parameters[1].getType()).isEqualTo(int.class);
        assertThat(parameters[1].getName()).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameters with cls, parameterTypes")
    void t7() {
        Parameter[] parameters = ClsUtil.getParameters(TestCar.class, new Class[]{String.class, int.class});

        assertThat(parameters[0].getType()).isEqualTo(String.class);
        assertThat(parameters[0].getName()).isEqualTo("name");

        assertThat(parameters[1].getType()).isEqualTo(int.class);
        assertThat(parameters[1].getName()).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameterNames with clsPath, parameters")
    void t8() {
        String[] parameterNames = ClsUtil.getParameterNames("com.ll.framework.ioc.util.sample.TestCar", new Object[]{"BMW", 1234});

        assertThat(parameterNames[0]).isEqualTo("name");
        assertThat(parameterNames[1]).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameterNames with clsPath, parameterTypes")
    void t9() {
        String[] parameterNames = ClsUtil.getParameterNames("com.ll.framework.ioc.util.sample.TestCar", new Class[]{String.class, int.class});

        assertThat(parameterNames[0]).isEqualTo("name");
        assertThat(parameterNames[1]).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameterNames with cls, parameters")
    void t10() {
        String[] parameterNames = ClsUtil.getParameterNames(TestCar.class, new Object[]{"BMW", 1234});

        assertThat(parameterNames[0]).isEqualTo("name");
        assertThat(parameterNames[1]).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameterNames with cls, parameterTypes")
    void t11() {
        String[] parameterNames = ClsUtil.getParameterNames(TestCar.class, new Class[]{String.class, int.class});

        assertThat(parameterNames[0]).isEqualTo("name");
        assertThat(parameterNames[1]).isEqualTo("number");
    }

    @Test
    @DisplayName("ClsUtil.getParameterNames with no args")
    void t12() {
        String[] parameterNames = ClsUtil.getParameterNames(TestPerson.class);

        assertThat(parameterNames[0]).isEqualTo("name");
        assertThat(parameterNames[1]).isEqualTo("age");
    }

    @Test
    @DisplayName("annotatedClasses")
    void t13() {
        Map<String, Class<?>> annotatedClasses = ClsUtil.annotatedClasses("com.ll", Component.class);

        assertThat(annotatedClasses).containsKeys("testFacadePostService", "testPostService", "testPostRepository");
        assertThat(annotatedClasses.get("testFacadePostService")).isEqualTo(TestFacadePostService.class);
        assertThat(annotatedClasses.get("testPostService")).isEqualTo(TestPostService.class);
        assertThat(annotatedClasses.get("testPostRepository")).isEqualTo(TestPostRepository.class);
    }
}