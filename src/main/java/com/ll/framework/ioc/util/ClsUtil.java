package com.ll.framework.ioc.util;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

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

    public static <T> Parameter[] getParameters(Class<T> cls, Object[] args) {
        Constructor<T> constructor = getConstructor(cls, args);

        return constructor.getParameters();
    }

    public static String[] getParameterNames(String clsPath, Object[] args) {
        return getParameterNames(loadClass(clsPath), args);
    }

    public static <T> String[] getParameterNames(Class<T> cls, Object[] args) {
        return Arrays.stream(
                        getParameters(cls, args)
                )
                .map(Parameter::getName)
                .toArray(String[]::new);
    }
}
