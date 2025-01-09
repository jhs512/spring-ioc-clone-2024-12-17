package com.ll.framework.ioc;

import com.ll.framework.ioc.util.ClsUtil;

public class BeanDefinition<T> {
    private final Class<T> cls;

    public BeanDefinition(Class<T> cls) {
        this.cls = cls;
    }

    public String[] getParameterNames() {
        return ClsUtil.getParameterNames(cls);
    }
}
