package com.madiot.poke.server.ladgt.executes;

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
import com.madiot.poke.server.base.IProcessExecute;
import com.madiot.poke.server.ladgt.context.PlayRoundContext;

import java.util.Date;

/**
 * Created by julian on 2017/8/29.
 */
public class SpeakExecute implements IProcessExecute<NoticeMessage> {


    @Override
    public byte[] execute(NoticeMessage noticeMessage, IPokeRedisService redisService, IMessageSendService messageSendService) {
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
}
