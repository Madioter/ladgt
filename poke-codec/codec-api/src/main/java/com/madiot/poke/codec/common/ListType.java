/**
 * @Title: ListType.java
 * @Package com.madiot.poke.codec.common
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.common;

import com.madiot.common.reflect.MetaClass;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;

import java.util.ArrayList;

/**
 * @ClassName: ListType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class ListType<T extends IComponent> extends ArrayList<T> implements IComponent {

    private Class<T> classType;

    public ListType(Class<T> classType) {
        this.classType = classType;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        int size = ByteUtils.bytesToInt(buffer.read(1));
        for (int i = 0; i < size; i++) {
            T component = MetaClass.newInstance(classType).getInstance();
            component.decode(buffer);
            add(component);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.write(ByteUtils.intToBytes(size(), 1));
        for (T component : this) {
            component.encode(buffer);
        }
    }
}
