/**
 * @Title: StaticDataSize.java
 * @Package common
 * @Description: 固定数据长度
 * @author Yi.Wang2
 * @date 2017/2/9
 * @version
 */
package com.madioter.poker.netty.message.type;

/**
 * @ClassName: StaticDataSize
 * @Description: 固定数据长度
 * @author Yi.Wang2
 * @date 2017/2/9
 */
public interface ISizeAble {

    /**
     * 获取数据长度
     * @return 数据长度，byte数
     */
    public int getSize();
}
