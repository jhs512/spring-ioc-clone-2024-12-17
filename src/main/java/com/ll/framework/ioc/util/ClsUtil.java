package com.ll.framework.ioc.util;

import com.ll.standard.util.Ut;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.reflections.scanners.Scanners.TypesAnnotated;

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

    public static <T> Constructor<T> getConstructor(Class<T> cls) {
        return (Constructor<T>) cls.getConstructors()[0];
    }

    public static <T> Constructor<T> getConstructor(Class<T> cls, Class<? extends Annotation> annotationClass) {
        return (Constructor<T>) Arrays.stream(cls.getConstructors())
                .filter(c -> c.isAnnotationPresent(annotationClass))
                .findFirst()
                .orElseGet(() -> getConstructor(cls));
    }

    @SneakyThrows
    private static <T> Constructor<T> getConstructor(Class<T> cls, Object[] args) {
        Class[] argTypes = getTypes(args);

        return cls.getConstructor(argTypes);
    }

    private static Class[] getTypes(Object[] args) {
        if (args instanceof Class[]) {
            return (Class[]) args;
        }

        return Arrays.stream(args)
                .map(e -> {
                    if (e instanceof Boolean) {
                        return boolean.class;
                    } else if (e instanceof Byte) {
                        return byte.class;
                    } else if (e instanceof Short) {
                        return short.class;
                    } else if (e instanceof Integer) {
                        return int.class;
                    } else if (e instanceof Long) {
                        return long.class;
                    } else if (e instanceof Float) {
                        return float.class;
                    } else if (e instanceof Double) {
                        return double.class;
                    } else if (e instanceof Character) {
                        return char.class;
                    }

                    return e.getClass();
                })
                .toArray(Class[]::new);
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

    public static String[] getParameterNames(String clsPath) {
        return getParameterNames(loadClass(clsPath));
    }

    public static <T> String[] getParameterNames(Class<T> cls) {
        Constructor<?> constructor = getConstructor(cls);

        return Arrays.stream(
                        constructor.getParameters()
                )
                .map(Parameter::getName)
                .toArray(String[]::new);
    }

    public static <T> String[] getParameterNames(Class<T> cls, Class<? extends Annotation> annotationClass) {
        Constructor<?> constructor = getConstructor(cls, annotationClass);

        return Arrays.stream(
                        constructor.getParameters()
                )
                .map(Parameter::getName)
                .toArray(String[]::new);
    }

    public static String[] getParameterNames(Executable executable) {
        return Arrays.stream(
                        executable.getParameters()
                )
                .map(Parameter::getName)
                .toArray(String[]::new);
    }

    public static Map<String, Class<?>> annotatedClasses(String prefix, Class<? extends Annotation> annotationCls) {
        Reflections reflections = new Reflections(prefix, TypesAnnotated);

        Map<String, Class<?>> clsMap = reflections.getTypesAnnotatedWith(annotationCls)
                .stream()
                .filter(cls -> !cls.isAnnotation())
                .collect(LinkedHashMap::new, (map, cls) -> map.put(Ut.str.lcfirst(cls.getSimpleName()), cls), Map::putAll);

        return clsMap;
    }
}
