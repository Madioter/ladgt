/**
 * @Title: IEffectiveAble.java
 * @Package com.igdata.gbparser.common.validate
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/4/26
 * @version
 */
package com.madioter.poker.netty.message.common.validate;


import com.madioter.poker.netty.message.type.ByteArrayType;

/**
 * @ClassName: IValidAble
 * @Description: 有效值判断接口
 * @author Yi.Wang2
 * @date 2017/4/26
 */
public interface IValid {

    public boolean isValid(ByteArrayType byteArrayType) throws InvalidException;
}
