/**
 * @Title: LadgtPlayer.java
 * @Package com.madiot.poke.codec.ladgt
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.model;

import com.madiot.poke.codec.common.IComponent;
import com.madiot.poke.codec.common.StringType;
import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;

/**
 * @ClassName: LadgtPlayer
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayer implements IComponent {

    private StringType name = new StringType();

    private Integer playerId;

    @Override
    public void decode(ByteBuffer buffer) {
        this.name.decode(buffer);
        this.playerId = ByteUtils.bytesToInt(buffer.read(4));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        this.name.encode(buffer);
        buffer.write(ByteUtils.intToBytes(this.playerId, 4));
    }

    public void setName(String name) {
        this.name.setString(name);
    }

    public String getName() {
        return name.toString();
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
}
