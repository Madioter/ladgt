/**
 * @Title: Ready.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.upstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtPlayer;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;

/**
 * @ClassName: Ready
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class Ready implements INoticeData {

    private INoticeResult result;

    private Integer roundIndex;

    private ListType<LadgtPlayer> playerList = new ListType<>(LadgtPlayer.class);

    public Ready(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.SUCCESS) {
            this.roundIndex = ByteUtils.bytesToInt(buffer.read(4));
            this.playerList.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.SUCCESS) {
            buffer.write(ByteUtils.intToBytes(this.roundIndex, 4));
            this.playerList.encode(buffer);
        }
    }

    public Integer getRoundIndex() {
        return roundIndex;
    }

    public void setRoundIndex(Integer roundIndex) {
        this.roundIndex = roundIndex;
    }

    public ListType<LadgtPlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ListType<LadgtPlayer> playerList) {
        this.playerList = playerList;
    }
}
