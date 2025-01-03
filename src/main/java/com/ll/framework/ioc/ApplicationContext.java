package com.ll.framework.ioc;

import com.ll.framework.ioc.util.ClsUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private Map<String, Object> beans;
    private String basePackage;

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        this.beans = new HashMap<>();
    }

    public void init() {
    }

    public <T> T genBean(String beanName) {
        Object bean = beans.get(beanName);

        if (bean == null) {
            String clsPath = switch (beanName) {
                case "testFacadePostService" -> "com.ll.domain.testPost.testPost.service.TestFacadePostService";
                case "testPostService" -> "com.ll.domain.testPost.testPost.service.TestPostService";
                case "testPostRepository" -> "com.ll.domain.testPost.testPost.repository.TestPostRepository";
                default -> null;
            };

            String[] parameterNames = ClsUtil.getParameterNames(clsPath);

            Object[] args = Arrays.stream(parameterNames)
                    .map(this::genBean)
                    .toArray();

            bean = ClsUtil.construct(clsPath, args);

            beans.put(beanName, bean);
        }

        return (T) bean;
    }
}
