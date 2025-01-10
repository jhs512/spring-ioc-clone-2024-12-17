package com.ll.framework.ioc;

import com.ll.framework.ioc.annotations.Autowired;
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
        this.makeMethod = ClsUtil.getConstructor(cls, Autowired.class);
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
        if (!isCreateTypeMethod()) return ClsUtil.getParameterNames(cls, Autowired.class);

        return ClsUtil.getParameterNames(makeMethod);
    }

    public boolean isCreateTypeMethod() {
        return makeMethod instanceof Method;
    }

    public T createBean(ApplicationContext applicationContext) {
        Object[] parameters = Arrays.stream(getParameterNames())
                .map(applicationContext::genBean)
                .toArray();

        T bean = !isCreateTypeMethod()
                ? createBeanByConstructor(applicationContext, parameters)
                : createBeanByMethod(applicationContext, parameters);

        fillFields(bean, applicationContext);

        return bean;
    }

    private void fillFields(T bean, ApplicationContext applicationContext) {
        Arrays.stream(
                        bean
                                .getClass()
                                .getDeclaredFields()
                )
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .forEach(f -> {
                    String beanName = f.getName();
                    Object _bean = applicationContext.genBean(beanName);

                    f.setAccessible(true);

                    try {
                        f.set(bean, _bean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @SneakyThrows
    private T createBeanByMethod(ApplicationContext applicationContext, Object[] parameters) {
        Method method = (Method) makeMethod;

        String configBeanName = Ut.str.lcfirst(method.getDeclaringClass().getSimpleName());

        return (T) method.invoke(applicationContext.genBean(configBeanName), parameters);
    }

    @SneakyThrows
    private T createBeanByConstructor(ApplicationContext applicationContext, Object[] parameters) {
        Constructor<T> constructor = (Constructor<T>) makeMethod;

        return constructor.newInstance(parameters);
    }
}
