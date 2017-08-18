package com.madiot.common.reflect;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by julian on 2017/8/17.
 */
public class MetaClass<T> {

    private static Map<Class, MetaClass> CACHE = new HashMap<>();

    private Class<T> clz;

    public static <T> MetaClass<T> newInstance(Class<T> clz) {
        if (CACHE.containsKey(clz)) {
            return CACHE.get(clz);
        }
        MetaClass<T> metaClass = new MetaClass(clz);
        CACHE.put(clz, metaClass);
        return metaClass;
    }

    public MetaClass(Class<T> clz) {
        this.clz = clz;
    }

    public T getInstance(Object... params) {
        Class[] paramTypes = new Class[params.length];
        if (ArrayUtils.isNotEmpty(params)) {
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
        }
        try {
            Constructor<T> constructor = clz.getConstructor(paramTypes);
            return constructor.newInstance(params);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ReflectException(clz.getName() + " new instance error, cause" + e.getCause(), e);
        }

    }
}
