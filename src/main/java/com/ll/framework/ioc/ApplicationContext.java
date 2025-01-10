package com.ll.framework.ioc;

import com.ll.framework.ioc.annotations.Bean;
import com.ll.framework.ioc.annotations.Component;
import com.ll.framework.ioc.annotations.Configuration;
import com.ll.framework.ioc.util.ClsUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ApplicationContext {
    private Map<String, Object> beans;
    private String basePackage;
    private Map<String, Class<?>> beanClasses;
    Map<String, BeanDefinition> beanDefinitions;

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        this.beans = new LinkedHashMap<>();
    }

    public void init() {
        this.beanClasses = ClsUtil.annotatedClasses(basePackage, Component.class);
        this.beanDefinitions = Stream.concat(
                ClsUtil.annotatedClasses(basePackage, Component.class)
                        .values()
                        .stream()
                        .map(BeanDefinition::new),
                ClsUtil.annotatedClasses(basePackage, Configuration.class)
                        .values()
                        .stream()
                        .flatMap(cls ->
                                Arrays.stream(cls.getMethods())
                                        .filter(m -> m.isAnnotationPresent(Bean.class))
                        )
                        .map(method -> new BeanDefinition(method.getDeclaringClass(), method.getName()))
        ).collect(LinkedHashMap::new, (map, bd) -> map.put(bd.getBeanName(), bd), Map::putAll);
    }

    public <T> T genBean(String beanName) {
        Object bean = beans.get(beanName);

        if (bean == null) {
            Class<T> cls = (Class<T>) beanClasses.get(beanName);

            String[] parameterNames = ClsUtil.getParameterNames(cls);

            Object[] args = Arrays.stream(parameterNames)
                    .map(this::genBean)
                    .toArray();

            bean = ClsUtil.construct(cls, args);

            beans.put(beanName, bean);
        }

        return (T) bean;
    }
}
