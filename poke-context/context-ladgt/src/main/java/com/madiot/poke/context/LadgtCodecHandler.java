/**
 * @Title: LadgtCodecHandler.java
 * @Package com.madiot.poke.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.context;

import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtCommandTypeEnum;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.model.LadgtPlayer;
import com.madiot.poke.codec.ladgt.model.LadgtPlayerScore;
import com.madiot.poke.codec.ladgt.notices.downstream.CallHelper;
import com.madiot.poke.codec.ladgt.notices.downstream.Deal;
import com.madiot.poke.codec.ladgt.notices.downstream.Discard;
import com.madiot.poke.codec.ladgt.notices.downstream.NoticeDiscard;
import com.madiot.poke.codec.ladgt.notices.downstream.NoticeHelper;
import com.madiot.poke.codec.ladgt.notices.downstream.NoticeScore;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poker.common.domain.IPlayer;
import com.madiot.poke.context.model.PlayScore;
import com.madiot.poke.context.observer.LadgtPlayObserver;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import com.madiot.common.utils.bytes.ByteBuffer;

import java.util.List;

/**
 * @author Yi.Wang2
 * @ClassName: LadgtCodecHandler
 * @Description: TODO
 * @date 2017/8/21
 */
public class LadgtCodecHandler {

    public static byte[] buildDealMessage(LadgtPlayObserver observer, IPlayer landlordPlayer) {
        Deal deal = (Deal) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.DEAL, LadgtNoticeResultEnum.COMMAND);
        ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);
        for (LadgtPokeCard pokeCard : observer.getPokeCards()) {
            cards.add(new LadgtCard(pokeCard.getIndex()));
        }
        deal.setCards(cards);
        deal.setRole(observer.getRole());
        deal.setLandlordPlayerId(landlordPlayer.getId());
        deal.setLandLordCard(new LadgtCard(observer.getLandlordCard().getIndex()));
        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.DEAL, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(deal);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    public static byte[] buildCallHelperMessage(LadgtPlayObserver observer) {
        CallHelper callHelper = (CallHelper) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.CALL_HELPER, LadgtNoticeResultEnum.COMMAND);
        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.CALL_HELPER, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(callHelper);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    public static byte[] buildDiscardMessage(LadgtPlayObserver observer, LadgtOneHand lastOneHand) {
        Discard discard = (Discard) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.DISCARD, LadgtNoticeResultEnum.COMMAND);
        ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);
        if (lastOneHand != null) {
            for (LadgtPokeCard pokeCard : lastOneHand.getCards()) {
                cards.add(new LadgtCard(pokeCard.getIndex()));
            }
            discard.setCards(cards);
        }

        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.DISCARD, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(discard);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    public static byte[] buildNoticeDiscardMessage(LadgtPlayObserver observer, LadgtOneHand oneHand) {
        NoticeDiscard noticeDiscard = (NoticeDiscard) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.NOTICE_DISCARD, LadgtNoticeResultEnum.COMMAND);
        noticeDiscard.setPlayerId(oneHand.getPlayerId());
        ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);
        if (oneHand != null) {
            for (LadgtPokeCard pokeCard : oneHand.getCards()) {
                cards.add(new LadgtCard(pokeCard.getIndex()));
            }
            noticeDiscard.setCards(cards);
        }

        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.NOTICE_DISCARD, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(noticeDiscard);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }


    public static byte[] buildHelperNoticeMessage(LadgtPlayObserver observer, LadgtPokeCard pokeCard) {
        NoticeHelper noticeHelper = (NoticeHelper) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.NOTICE_HELPER, LadgtNoticeResultEnum.COMMAND);
        noticeHelper.setRole(observer.getRole());
        noticeHelper.setPokeCard(new LadgtCard(pokeCard.getIndex()));

        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.NOTICE_HELPER, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(noticeHelper);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    public static byte[] buildScoreMessage(LadgtPlayObserver observer, List<PlayScore> scores) {
        NoticeScore noticeScore = (NoticeScore) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.NOTICE_SCORE, LadgtNoticeResultEnum.COMMAND);
        ListType<LadgtPlayerScore> playerScores = new ListType<>(LadgtPlayerScore.class);
        for (PlayScore score : scores) {
            LadgtPlayerScore playerScore = new LadgtPlayerScore();
            playerScore.setPlayer(new LadgtPlayer(score.getPlayerName(), score.getPlayerId(), 0, score.getLastScore()));
            playerScore.setRole(observer.getRole());
            playerScore.setScore(score.getScore());
            playerScores.add(playerScore);
        }
        noticeScore.setPlayerScores(playerScores);

        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.NOTICE_SCORE, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(noticeScore);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    public static ListType<LadgtPlayer> getPlayers(IPlayRound playRound) {
        List<LadgtPlayObserver> observers = playRound.getPlayers();
        ListType<LadgtPlayer> players = new ListType<>(LadgtPlayer.class);
        for (LadgtPlayObserver playObserver : observers) {
            LadgtPlayer ladgtPlayer = new LadgtPlayer(playObserver.getPlayer().getName(), playObserver.getPlayer().getId(),
                    playObserver.getPokeCards().size(), playObserver.getPlayer().getLastScore());
            players.add(ladgtPlayer);
        }
        return players;
    }

    public static ListType<LadgtCard> getCards(IPlayRound playRound, Integer playerId) {
        List<LadgtPlayObserver> observers = playRound.getPlayers();
        ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);
        for (LadgtPlayObserver playObserver : observers) {
            if (playObserver.getPlayer().getId().equals(playerId)) {
                for (LadgtPokeCard ladgtPokeCard : playObserver.getPokeCards()) {
                    cards.add(new LadgtCard(ladgtPokeCard.getIndex()));
                }
            }
        }
        return cards;
    }
}
