/**
 * @Title: LadgtPokeCard.java
 * @Package com.madiot.poke.codec.ladgt.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.model;

import com.madiot.poke.codec.common.IComponent;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: LadgtPokeCard
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtCard implements IComponent {

    private Integer index;

    public LadgtCard() {

    }

    public LadgtCard(int index) {
        this.index = index;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        this.index = buffer.readInt();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.writeInt(this.index);
    }

    public Integer getIndex() {
        return index;
    }
}
