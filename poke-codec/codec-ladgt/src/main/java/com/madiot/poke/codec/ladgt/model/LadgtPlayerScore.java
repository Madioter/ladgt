/**
 * @Title: LadgtPlayerScore.java
 * @Package com.madiot.poke.codec.ladgt.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.model;

import com.madiot.poke.codec.common.IComponent;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;

/**
 * @ClassName: LadgtPlayerScore
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayerScore implements IComponent {

    private LadgtPlayer player;

    private LadgtRoleEnum role;

    private Integer score;

    @Override
    public void decode(ByteBuffer buffer) {
        this.player.decode(buffer);
        this.role = LadgtRoleEnum.get(buffer.readInt());
        this.score = ByteUtils.bytesToInt(buffer.read(4));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        this.player.encode(buffer);
        buffer.writeInt(this.role.getCode());
        buffer.write(ByteUtils.intToBytes(this.score, 4));
    }

    public LadgtPlayer getPlayer() {
        return player;
    }

    public void setPlayer(LadgtPlayer player) {
        this.player = player;
    }

    public LadgtRoleEnum getRole() {
        return role;
    }

    public void setRole(LadgtRoleEnum role) {
        this.role = role;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


}
