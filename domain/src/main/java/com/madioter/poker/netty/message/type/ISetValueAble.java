/**
 * @Title: ISetValueAble.java
 * @Package common.data
 * @Description: 赋值接口
 * @author Yi.Wang2
 * @date 2017/2/9
 * @version
 */
package com.madioter.poker.netty.message.type;

/**
 * @ClassName: ISetValueAble
 * @Description: 赋值接口
 * @author Yi.Wang2
 * @date 2017/2/9
 */
public interface ISetValueAble<E> {

    /**
     * 给对象赋值
     * @param value 值
     */
    public void setValue(E value);
}
