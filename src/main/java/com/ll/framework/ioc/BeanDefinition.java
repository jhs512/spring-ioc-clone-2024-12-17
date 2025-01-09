package com.ll.framework.ioc;

import com.ll.framework.ioc.util.ClsUtil;
import com.ll.standard.util.Ut;

public class BeanDefinition<T> {
    private final Class<T> cls;

    public BeanDefinition(Class<T> cls) {
        this.cls = cls;
    }

    public String[] getParameterNames() {
        return ClsUtil.getParameterNames(cls);
    }

    public String getBeanName() {
        return Ut.str.lcfirst(cls.getSimpleName());
    }

    public boolean isCreateTypeMethod() {
        return false;
    }
}
