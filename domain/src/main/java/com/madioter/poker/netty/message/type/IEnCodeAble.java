/**
 * @Title: ItemData.java
 * @Package common
 * @Description: 可编码接口
 * @author Yi.Wang2
 * @date 2017/2/9
 * @version
 */
package com.madioter.poker.netty.message.type;


import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: ItemData
 * @Description: 可编码接口
 * @author Yi.Wang2
 * @date 2017/2/9
 */
public interface IEnCodeAble {

    /**
     * 编码
     * @return 编码后的byte数组
     */
    public void encode(ByteBuffer buffer);
}
