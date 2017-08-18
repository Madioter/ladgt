/**
 * @Title: EnumUtils.java
 * @Package common
 * @Description: 枚举工具
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.common.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: EnumUtils
 * @Description: 枚举工具
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public final class EnumUtils {

    /**
     * 通过枚举类型和枚举编码，获取一个枚举对象
     * @param clz 枚举类型
     * @param code 枚举编码
     * @param <E> 枚举泛型
     * @return 枚举对象
     * @throws NoSuchMethodException 找不到类方法
     * @throws InvocationTargetException 找不到对应的类
     * @throws IllegalAccessException 类方法是私有的无法访问
     */
    public static <E extends IEnum> E get(Class<E> clz, int code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clz.getDeclaredMethod("get", Integer.class);
        return (E) method.invoke(null, code);
    }
}
