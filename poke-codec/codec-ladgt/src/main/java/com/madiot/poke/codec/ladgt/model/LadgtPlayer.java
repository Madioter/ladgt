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
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;

/**
 * @ClassName: LadgtPlayer
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayer implements IComponent {

    private StringType name = new StringType();

    private Integer playerId;

    private Integer lastCards = 0;

    private Integer lastScore;

    public LadgtPlayer(String name, Integer playerId, Integer lastCards, Integer lastScore) {
        this.name.setString(name);
        this.playerId = playerId;
        this.lastCards = lastCards;
        this.lastScore = lastScore;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        this.name.decode(buffer);
        this.playerId = ByteUtils.bytesToInt(buffer.read(4));
        this.lastCards = buffer.readInt();
        this.lastScore = ByteUtils.bytesToInt(buffer.read(4));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        this.name.encode(buffer);
        buffer.write(ByteUtils.intToBytes(this.playerId, 4));
        buffer.writeInt(lastCards);
        buffer.write(ByteUtils.intToBytes(this.lastScore, 4));
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

    public Integer getLastCards() {
        return lastCards;
    }

    public void setLastCards(Integer lastCards) {
        this.lastCards = lastCards;
    }

    public Integer getLastScore() {
        return lastScore;
    }

    public void setLastScore(Integer lastScore) {
        this.lastScore = lastScore;
    }
}
