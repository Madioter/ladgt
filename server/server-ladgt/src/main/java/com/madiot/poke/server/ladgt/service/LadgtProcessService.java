/**
 * @Title: LadgtProcessService.java
 * @Package com.madiot.poke.server.ladgt.service
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.server.ladgt.service;

import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.poke.codec.common.StringType;
import com.madiot.poke.codec.ladgt.LadgtCommandTypeEnum;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.notices.downstream.SpeakNotice;
import com.madiot.poke.codec.ladgt.notices.upstream.Speak;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.context.api.IPlayer;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.poke.dubbo.api.server.IProcessService;
import com.madiot.poke.server.base.IProcessExecute;
import com.madiot.poke.server.base.ProcessExecuteContext;
import com.madiot.poke.server.ladgt.context.PlayRoundContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Yi.Wang2
 * @ClassName: LadgtProcessService
 * @Description: TODO
 * @date 2017/8/24
 */
@Service
public class LadgtProcessService implements IProcessService {

    @Autowired
    private IPokeRedisService redisService;

    @Autowired
    private IMessageSendService messageSendService;

    @Autowired
    private ProcessExecuteContext processExecuteContext;

    @Override
    public byte[] receiptMessage(byte[] message) {
        NoticeMessage noticeMessage = new NoticeMessage(message);
        IProcessExecute<NoticeMessage> processExecute = processExecuteContext.getService(noticeMessage.getType().toString());
        if (processExecute == null) {
            return new byte[0];
        }
        return processExecute.execute(noticeMessage, redisService, messageSendService);
    }
}
