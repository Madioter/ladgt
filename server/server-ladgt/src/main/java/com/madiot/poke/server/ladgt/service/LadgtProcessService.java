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
import com.madiot.poke.server.ladgt.context.PlayRoundContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName: LadgtProcessService
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class LadgtProcessService implements IProcessService {

    @Autowired
    private IPokeRedisService redisService;

    @Autowired
    private IMessageSendService messageSendService;

    @Override
    public byte[] receiptMessage(byte[] message) {
        NoticeMessage noticeMessage = new NoticeMessage(message);
        if (noticeMessage.getType() == LadgtCommandTypeEnum.DEAL
                || noticeMessage.getType() == LadgtCommandTypeEnum.NOTICE_HELPER
                || noticeMessage.getType() == LadgtCommandTypeEnum.NOTICE_DISCARD
                || noticeMessage.getType() == LadgtCommandTypeEnum.NOTICE_SCORE
                || noticeMessage.getType() == LadgtCommandTypeEnum.NOTICE_SPEAK) {
            return new byte[0];
        } else if (noticeMessage.getType() == LadgtCommandTypeEnum.CALL_HELPER || noticeMessage.getType() == LadgtCommandTypeEnum.DISCARD) {
            return callback(noticeMessage);
        } else if (noticeMessage.getType() == LadgtCommandTypeEnum.SPEAK) {
            return noticeSpeak(noticeMessage);
        } else if (noticeMessage.getType() == LadgtCommandTypeEnum.CURRENT_STATE) {
            return currentState(noticeMessage);
        }
        return null;
    }

    private byte[] currentState(NoticeMessage noticeMessage) {
        return new byte[0];
    }

    private byte[] noticeSpeak(NoticeMessage noticeMessage) {
        String message = ((Speak) noticeMessage.getData()).getMessage().getString();
        ConnectInfoDO connectInfo = redisService.getConnectInfo(noticeMessage.getPlayerId());
        IPlayRound playRound = PlayRoundContext.get(connectInfo.getRoundIndex());
        NoticeMessage broadcastMessage = new NoticeMessage(null, LadgtCommandTypeEnum.NOTICE_SPEAK, LadgtNoticeResultEnum.COMMAND);
        SpeakNotice speakNotice = new SpeakNotice(LadgtNoticeResultEnum.COMMAND);
        speakNotice.setPlayerId(noticeMessage.getPlayerId());
        speakNotice.setMessage(new StringType(message));
        for (IPlayObserver playerObserver : playRound.getPlayers()) {
            IPlayer player = playerObserver.getPlayer();
            if (!player.getId().equals(noticeMessage.getPlayerId())) {
                ByteBuffer buffer = new ByteBuffer();
                broadcastMessage.setNoticeData(speakNotice);
                broadcastMessage.getNoticeHead().setUserId(player.getId());
                broadcastMessage.encode(buffer);
                messageSendService.sendMessage(buffer.getBytes(), player.getServerIp(), player.getId());
            }
        }
        noticeMessage.getNoticeHead().setResult(LadgtNoticeResultEnum.SUCCESS);
        noticeMessage.getNoticeHead().setTimestamp(new Date());
        ByteBuffer buffer = new ByteBuffer();
        noticeMessage.encode(buffer);
        return buffer.getBytes();
    }

    private byte[] callback(NoticeMessage noticeMessage) {
        ConnectInfoDO connectInfo = redisService.getConnectInfo(noticeMessage.getPlayerId());
        Integer roundIndex = connectInfo.getRoundIndex();
        IPlayRound playRound = PlayRoundContext.get(roundIndex);
        playRound.setReceipt(noticeMessage);
        return new byte[0];
    }
}
