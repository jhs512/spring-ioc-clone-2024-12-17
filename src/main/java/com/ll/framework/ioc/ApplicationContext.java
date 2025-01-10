package com.ll.framework.ioc;

import com.ll.framework.ioc.annotations.Component;
import com.ll.framework.ioc.util.ClsUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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
        this.beanDefinitions = ClsUtil.annotatedClasses(basePackage, Component.class)
                .values()
                .stream()
                .collect(LinkedHashMap::new, (map, cls) -> {
                    BeanDefinition beanDefinition = new BeanDefinition(cls);
                    map.put(beanDefinition.getBeanName(), beanDefinition);
                }, Map::putAll);
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
