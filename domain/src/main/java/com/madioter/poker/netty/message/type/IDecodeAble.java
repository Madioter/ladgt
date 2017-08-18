/**
 * @Title: IDecodeAble.java
 * @Package common.data
 * @Description: 可解码接口
 * @author Yi.Wang2
 * @date 2017/2/9
 * @version
 */
package com.madioter.poker.netty.message.type;

import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: IDecodeAble
 * @Description: 可解码接口
 * @author Yi.Wang2
 * @date 2017/2/9
 */
public interface IDecodeAble {

    /**
     * 解码
     * @param code 比特数组
     */
    public void decode(ByteBuffer code);
}
