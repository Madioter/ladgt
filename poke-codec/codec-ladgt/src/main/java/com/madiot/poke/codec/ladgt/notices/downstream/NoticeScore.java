/**
 * @Title: NoticeScore.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.downstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtPlayerScore;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: NoticeScore
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class NoticeScore implements INoticeData {

    private INoticeResult result;

    private ListType<LadgtPlayerScore> playerScores = new ListType<>(LadgtPlayerScore.class);

    public NoticeScore(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            playerScores.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            playerScores.encode(buffer);
        }
    }

    public ListType<LadgtPlayerScore> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(ListType<LadgtPlayerScore> playerScores) {
        this.playerScores = playerScores;
    }
}
