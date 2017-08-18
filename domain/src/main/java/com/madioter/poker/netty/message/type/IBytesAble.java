/**
 * @Title: IBytesAble.java
 * @Package common.data
 * @Description: 获取byte数组接口
 * @author Yi.Wang2
 * @date 2017/2/13
 * @version
 */
package com.madioter.poker.netty.message.type;

/**
 * @ClassName: IBytesAble
 * @Description: 获取byte数组接口
 * @author Yi.Wang2
 * @date 2017/2/13
 */
public interface IBytesAble {

    /**
     * 获取对象的byte数组表示
     * @return byte数组
     */
    public abstract byte[] getBytes();
}
