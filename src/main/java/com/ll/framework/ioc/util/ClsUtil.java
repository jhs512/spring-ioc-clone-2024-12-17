package com.ll.framework.ioc.util;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class ClsUtil {
    @SneakyThrows
    public static <T> Class<T> loadClass(String clsPath) {
        return (Class<T>) Class.forName(clsPath);
    }

    public static <T> T construct(String clsPath, Object[] args) {
        return construct(loadClass(clsPath), args);
    }

    @SneakyThrows
    public static <T> T construct(Class<T> cls, Object[] args) {
        Constructor<T> constructor = getConstructor(cls, args);

        return constructor.newInstance(args);
    }

    private static <T> Constructor<T> getConstructor(Class<T> cls, Object[] args) {
        return (Constructor<T>) cls.getDeclaredConstructors()[0];
    }

    public static Parameter[] getParameters(String clsPath, Object[] args) {
        return getParameters(loadClass(clsPath), args);
    }

    public static Parameter[] getParameters(Class<?> cls, Object[] args) {
        Constructor<?> constructor = getConstructor(cls, args);

        return constructor.getParameters();
    }
}
