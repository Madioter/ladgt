package com.madiot.poke.server.ladgt.executes;

import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.poke.server.base.IProcessExecute;
import com.madiot.poke.server.ladgt.context.PlayRoundContext;

/**
 * Created by julian on 2017/8/29.
 */
public class CallbackExecute implements IProcessExecute<NoticeMessage> {
    @Override
    public byte[] execute(NoticeMessage noticeMessage, IPokeRedisService redisService, IMessageSendService messageSendService) {
        ConnectInfoDO connectInfo = redisService.getConnectInfo(noticeMessage.getPlayerId());
        Integer roundIndex = connectInfo.getRoundIndex();
        IPlayRound playRound = PlayRoundContext.get(roundIndex);
        playRound.setReceipt(noticeMessage);
        return new byte[0];
    }
}
