package com.madiot.poke.server.ladgt.executes;

import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.poke.server.base.IProcessExecute;

/**
 * Created by julian on 2017/8/29.
 */
public class NoResultExecute implements IProcessExecute<NoticeMessage> {
    @Override
    public byte[] execute(NoticeMessage message, IPokeRedisService redisService, IMessageSendService messageSendService) {
        return new byte[0];
    }
}
