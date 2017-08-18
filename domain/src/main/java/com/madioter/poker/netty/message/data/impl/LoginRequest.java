/**
 * @Title: loginMessage.java
 * @Package com.madioter.poker.netty.message.data.impl
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/5/11
 * @version
 */
package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.StringType;

/**
 * @ClassName: LoginMessage
 * @Description: 用户登录信息
 * @author Yi.Wang2
 * @date 2017/5/11
 */
public class LoginRequest implements IMessageData {

    /**
     * 用户名
     */
    private StringType username = new StringType(20);

    /**
     * 密码
     */
    private StringType password = new StringType(20);

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = new ByteBuffer();
        encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    @Override
    public void decode(ByteBuffer code) {
        username.decode(code);
        password.decode(code);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        username.encode(buffer);
        password.encode(buffer);
    }
}
