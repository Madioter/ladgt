/**
 * @Title: LadgtProcessService.java
 * @Package com.madiot.poke.server.ladgt.service
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.server.ladgt.service;

import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.poke.dubbo.api.server.IProcessService;
import com.madiot.poke.server.base.IProcessExecute;
import com.madiot.poke.server.base.ProcessExecuteContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return processExecute.execute(noticeMessage);
    }
}
