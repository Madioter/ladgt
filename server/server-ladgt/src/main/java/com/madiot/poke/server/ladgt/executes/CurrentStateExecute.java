package com.madiot.poke.server.ladgt.executes;

import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.common.spring.SpringContextUtils;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.model.LadgtPlayer;
import com.madiot.poke.codec.ladgt.notices.upstream.CurrentState;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.LadgtCodecHandler;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.context.observer.LadgtPlayObserver;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import com.madiot.poke.server.base.IProcessExecute;
import com.madiot.poke.server.ladgt.context.PlayRoundContext;

import java.util.Date;
import java.util.List;

/**
 * Created by julian on 2017/8/29.
 */
public class CurrentStateExecute implements IProcessExecute<NoticeMessage> {

    private PlayRoundContext playRoundContext = SpringContextUtils.getBeanByClass(PlayRoundContext.class);

    @Override
    public byte[] execute(NoticeMessage noticeMessage) {
        Integer playerId = noticeMessage.getPlayerId();
        Integer roundIndex = ((CurrentState) noticeMessage.getData()).getRoundIndex();
        IPlayRound playRound = playRoundContext.get(roundIndex);
        ListType<LadgtPlayer> players = LadgtCodecHandler.getPlayers(playRound);
        ListType<LadgtCard> cards = LadgtCodecHandler.getCards(playRound,playerId);

        CurrentState currentState = new CurrentState(LadgtNoticeResultEnum.SUCCESS);
        currentState.setCards(cards);
        currentState.setPlayers(players);
        currentState.setRoundIndex(roundIndex);

        noticeMessage.setNoticeData(currentState);
        noticeMessage.getNoticeHead().setResult(LadgtNoticeResultEnum.SUCCESS);
        noticeMessage.getNoticeHead().setTimestamp(new Date());
        ByteBuffer buffer = new ByteBuffer();
        noticeMessage.encode(buffer);
        return buffer.getBytes();
    }
}
