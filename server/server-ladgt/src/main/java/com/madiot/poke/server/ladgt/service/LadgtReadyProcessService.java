package com.madiot.poke.server.ladgt.service;

import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtPlayer;
import com.madiot.poke.codec.ladgt.notices.upstream.Ready;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.LadgtCodecHandler;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.dubbo.api.server.IProcessService;
import com.madiot.poke.dubbo.api.server.IReadyProcessService;
import com.madiot.poke.server.ladgt.context.PlayRoundContext;
import com.madiot.poker.common.future.CallbackFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by julian on 2017/8/30.
 */
@Service
public class LadgtReadyProcessService implements IReadyProcessService {

    @Autowired
    private PlayRoundContext playRoundContext;

    @Autowired
    private IPokeRedisService redisService;

    @Override
    public byte[] receiptMessage(byte[] message) {
        NoticeMessage noticeMessage = new NoticeMessage(message);
        Integer playerId = noticeMessage.getPlayerId();
        CallbackFuture<Integer> future = playRoundContext.addPlayer(playerId);
        try {
            Integer roundIndex = future.get();
            IPlayRound playRound = playRoundContext.get(roundIndex);
            Ready ready = new Ready(LadgtNoticeResultEnum.SUCCESS);
            ready.setRoundIndex(roundIndex);
            ListType<LadgtPlayer> players = LadgtCodecHandler.getPlayers(playRound);
            ready.setPlayerList(players);

            ConnectInfoDO connectInfoDO = redisService.getConnectInfo(playerId);
            connectInfoDO.setRoundIndex(roundIndex);
            redisService.saveConnectInfo(connectInfoDO);

            noticeMessage.getNoticeHead().setTimestamp(new Date());
            noticeMessage.getNoticeHead().setResult(LadgtNoticeResultEnum.SUCCESS);
            noticeMessage.setNoticeData(ready);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            noticeMessage.getNoticeHead().setTimestamp(new Date());
            noticeMessage.getNoticeHead().setResult(LadgtNoticeResultEnum.ERROR);
        }
        ByteBuffer buffer = new ByteBuffer();
        noticeMessage.encode(buffer);
        return buffer.getBytes();
    }
}
