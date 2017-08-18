package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.common.enums.RoleEnum;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.ByteType;
import com.madioter.poker.netty.message.type.impl.EnumType;
import com.madioter.poker.netty.message.type.impl.WordType;

/**
 * Created by julian on 2017/5/16.
 */
public class JoinResponse implements IMessageData{

    /**
     * 桌号
     */
    private WordType deskId = new WordType();

    /**
     * 参与角色
     */
    private EnumType<RoleEnum> role = new EnumType<RoleEnum>(RoleEnum.class);

    /**
     * 桌序
     */
    private ByteType index = new ByteType();

    public WordType getDeskId() {
        return deskId;
    }

    public void setDeskId(WordType deskId) {
        this.deskId = deskId;
    }

    public EnumType<RoleEnum> getRole() {
        return role;
    }

    public void setRole(EnumType<RoleEnum> role) {
        this.role = role;
    }

    public ByteType getIndex() {
        return index;
    }

    public void setIndex(ByteType index) {
        this.index = index;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        deskId.encode(buffer);
        role.encode(buffer);
        index.encode(buffer);
    }

    @Override
    public void decode(ByteBuffer code) {
        deskId.decode(code);
        role.decode(code);
        index.decode(code);
    }
}
