package com.ll.framework.ioc;

import com.ll.framework.ioc.util.ClsUtil;
import com.ll.standard.util.Ut;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class BeanDefinition<T> {
    private final Class<T> cls;
    private final Executable makeMethod;
    @Getter
    private final String beanName;

    public BeanDefinition(Class<T> cls) {
        this.cls = cls;
        this.makeMethod = ClsUtil.getConstructor(cls);
        this.beanName = Ut.str.lcfirst(cls.getSimpleName());
    }

    public BeanDefinition(Class<?> configClass, String beanName) {
        Method method = Arrays.stream(configClass
                        .getMethods())
                .filter(m -> m.getName().equals(beanName))
                .findFirst()
                .get();

        this.cls = (Class<T>) method.getReturnType();
        this.makeMethod = method;
        this.beanName = beanName;
    }

    public String[] getParameterNames() {
        if (!isCreateTypeMethod()) return ClsUtil.getParameterNames(cls);

        return ClsUtil.getParameterNames(makeMethod);
    }

    public boolean isCreateTypeMethod() {
        return makeMethod instanceof Method;
    }

    public T createBean(ApplicationContext applicationContext) {
        if (!isCreateTypeMethod()) {
            return createBeanByConstructor(applicationContext);
        }

        return createBeanByMethod(applicationContext);
    }

    @SneakyThrows
    private T createBeanByMethod(ApplicationContext applicationContext) {
        Object[] parameters = Arrays.stream(getParameterNames())
                .map(applicationContext::genBean)
                .toArray();

        Method method = (Method) makeMethod;

        String configBeanName = Ut.str.lcfirst(method.getDeclaringClass().getSimpleName());

        return (T) method.invoke(applicationContext.genBean(configBeanName), parameters);
    }

    @SneakyThrows
    private T createBeanByConstructor(ApplicationContext applicationContext) {
        Object[] parameters = Arrays.stream(getParameterNames())
                .map(applicationContext::genBean)
                .toArray();

        Constructor<T> constructor = (Constructor<T>) makeMethod;

        return constructor.newInstance(parameters);
    }
}
