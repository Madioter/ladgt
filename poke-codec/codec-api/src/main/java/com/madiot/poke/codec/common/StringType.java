/**
 * @Title: StringType.java
 * @Package com.madiot.poke.codec.common
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.common;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;

/**
 * @ClassName: StringType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class StringType implements IComponent {

    private String data;

    @Override
    public void decode(ByteBuffer buffer) {
        int length = buffer.readInt();
        this.data = new String(buffer.read(length));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        byte[] bytes = data.getBytes();
        buffer.write(ByteUtils.intToBytes(bytes.length, 1));
        buffer.write(bytes);
    }

    public String getString() {
        return toString();
    }

    public void setString(String string) {
        this.data = string;
    }

    @Override
    public String toString() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
