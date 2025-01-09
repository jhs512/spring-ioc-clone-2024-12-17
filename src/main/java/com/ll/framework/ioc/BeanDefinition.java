package com.ll.framework.ioc;

import com.ll.framework.ioc.util.ClsUtil;
import com.ll.standard.util.Ut;
import lombok.Getter;

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
        return ClsUtil.getParameterNames(cls);
    }

    public boolean isCreateTypeMethod() {
        return makeMethod instanceof Method;
    }
}
