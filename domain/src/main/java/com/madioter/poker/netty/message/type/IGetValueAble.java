/**
 * @Title: IGetValueAble.java
 * @Package common.data
 * @Description: 取值接口
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.type;

/**
 * @ClassName: IGetValueAble
 * @Description: 取值接口
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public interface IGetValueAble<E> {

    /**
     * 取值接口
     * @return 对象的值
     */
    public E getValue();
}
