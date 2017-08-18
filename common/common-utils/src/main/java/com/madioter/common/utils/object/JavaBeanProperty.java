package com.madioter.common.utils.object;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * java对象属性设置和取值类
 * Created by Yi.Wang2 on 2016/12/16.
 */
public class JavaBeanProperty {

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * set方法
     */
    private Method writeMethod;

    /**
     * 属性类型
     */
    private Class propertyType;

    /**
     * get方法
     */
    private Method readMethod;

    /**
     * 构造方法
     * @param descriptor 属性描述器
     */
    public JavaBeanProperty(PropertyDescriptor descriptor) {
        this.propertyName = descriptor.getName();
        this.writeMethod = descriptor.getWriteMethod();
        this.readMethod = descriptor.getReadMethod();
        this.propertyType = descriptor.getPropertyType();
    }

    /**
     * 向对象的属性中写数据
     *
     * @param propertyValue 属性值
     * @param bean          java对象
     * @throws InvocationTargetException,IllegalAccessException set值异常
     */
    public void setValue(Object bean, Object propertyValue) throws InvocationTargetException, IllegalAccessException {
        if (writeMethod != null) {
            writeMethod.invoke(bean, new Object[]{propertyValue});
        }
    }

    /**
     * 从对象的属性中读取值
     *
     * @param bean JAVA对象
     * @return 属性值
     * @throws InvocationTargetException, IllegalAccessException 获取数据值异常
     */
    public Object getValue(Object bean) throws InvocationTargetException, IllegalAccessException {
        if (readMethod != null) {
            return readMethod.invoke(bean, new Object[0]);
        }
        return null;
    }

    /**
     * 获取属性名
     *
     * @return 属性名
     */
    public String getName() {
        return propertyName;
    }

    /**
     * 获取属性类型
     *
     * @return 属性类型
     */
    public Class getType() {
        return propertyType;
    }
}
