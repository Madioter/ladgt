/**
 * @Title: SpeakNotice.java
 * @Package com.madiot.poke.codec.ladgt.notices.downstream
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.downstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.StringType;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;

/**
 * @ClassName: SpeakNotice
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 */
public class SpeakNotice implements INoticeData {

    private INoticeResult result;

    private StringType message;

    private int playerId;

    public SpeakNotice(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        this.playerId = ByteUtils.bytesToInt(buffer.read(4));
        this.message.decode(buffer);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.write(ByteUtils.intToBytes(this.playerId, 4));
        this.message.decode(buffer);
    }

    public StringType getMessage() {
        return message;
    }

    public void setMessage(StringType message) {
        this.message = message;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
