/**
 * @Title: LoginResponse.java
 * @Package com.madioter.poker.netty.message.data.impl
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/5/11
 * @version
 */
package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.ByteType;
import com.madioter.poker.netty.message.type.impl.DateTime;
import com.madioter.poker.netty.message.type.impl.StringType;

/**
 * @ClassName: LoginResponse
 * @Description: 登录响应
 * @author Yi.Wang2
 * @date 2017/5/11
 */
public class LoginResponse implements IMessageData {

    /**
     * 有效期
     */
    private DateTime expireTime = new DateTime();

    /**
     * 用户昵称
     */
    private StringType nickName = new StringType(20);

    /**
     * 会员级别
     */
    private ByteType memberRating = new ByteType();

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void decode(ByteBuffer code) {
        expireTime.decode(code);
        nickName.decode(code);
        memberRating.decode(code);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        expireTime.encode(buffer);
        nickName.encode(buffer);
        memberRating.encode(buffer);
    }
}
