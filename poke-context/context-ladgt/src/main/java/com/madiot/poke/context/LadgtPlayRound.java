/**
 * @Title: LadgtPokeRound.java
 * @Package com.madiot.poke.ladgt.play.round
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context;

import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtCommandTypeEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;
import com.madiot.poke.codec.ladgt.notices.downstream.CallHelper;
import com.madiot.poke.codec.ladgt.notices.downstream.Discard;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.api.IDistributional;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.context.api.IPlayer;
import com.madiot.poke.context.config.LadgtConfiguration;
import com.madiot.poke.context.exception.ContextException;
import com.madiot.poke.context.model.PlayObserverList;
import com.madiot.poke.context.model.PlayScore;
import com.madiot.poke.context.observer.LadgtPlayObserver;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import com.madioter.poker.common.future.CallbackFuture;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: LadgtPokeRound
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtPlayRound implements IPlayRound {

    private final PlayObserverList<LadgtPlayObserver> observers;
    private LadgtConfiguration configuration;
    private LadgtOneHand lastHand;
    private IPlayer landlordPlayer;
    private int roundIndex;
    private AtomicInteger index = new AtomicInteger(0);

    private CallbackFuture<NoticeMessage> result;

    public LadgtPlayRound(LadgtConfiguration configuration, List<LadgtPlayObserver> observers, int roundIndex) {
        this.configuration = configuration;
        this.observers = new PlayObserverList<>(observers);
        this.roundIndex = roundIndex;
    }

    public LadgtOneHand getLastHand() {
        return lastHand;
    }

    public void setLastHand(LadgtOneHand lastHand) {
        this.lastHand = lastHand;
    }

    public void deal() {
        IDistributional<LadgtPokeCard> distributional = new LadgtDistributional();
        configuration.getPokeCardFactory().distributional(distributional);
        int index = distributional.getLandlordIndex();
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).deal(distributional.getNextList(i), distributional.getLandlordCard());
            if (observers.get(i).isHaveTaggedCard()) {
                observers.get(i).addCards(distributional.getNextList(Integer.MAX_VALUE));
            }
            if (i == index && observers.get(i).isHaveTaggedCard()) {
                observers.get(i).setRole(LadgtRoleEnum.LAND_LORD_WITH_HELPER);
                landlordPlayer = observers.get(i).getPlayer();
            } else if (i == index) {
                observers.get(i).setRole(LadgtRoleEnum.LAND_LORD);
                landlordPlayer = observers.get(i).getPlayer();
            } else if (observers.get(i).isHaveTaggedCard()) {
                observers.get(i).setRole(LadgtRoleEnum.HELPER);
            } else {
                observers.get(i).setRole(LadgtRoleEnum.FARMER);
            }
        }

        for (IPlayObserver player : observers) {
            player.sendDealMessage(landlordPlayer);
        }
    }

    @Override
    public void callHelper(int timeOut) {
        Integer cardIndex = null;
        for (IPlayObserver player : observers) {
            this.result = player.sendCallHelperMessage();
            if (this.result != null) {
                cardIndex = dealResult(this.result, player, timeOut);
            }
        }
        if (cardIndex != null) {
            for (IPlayObserver player : observers) {
                player.setHelperRole(cardIndex);
            }
        }
    }

    @Override
    public boolean isEnd() {
        for (IPlayObserver playObserver : observers) {
            if (playObserver.isEnd()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void round(int timeout) {
        if (lastHand != null) {
            this.observers.markRound();
        }
        while (observers.hasNext()) {
            IPlayObserver player = observers.getNext();
            if (lastHand == null && index.get() == 0 && landlordPlayer != player.getPlayer()) {
                continue;
            }
            this.result = player.discard(lastHand);
            LadgtOneHand oneHand = null;
            try {
                NoticeMessage message = this.result.get(timeout, TimeUnit.SECONDS);
                Discard discard = (Discard) message.getData();
                oneHand = getOneHand(discard.getCards(), player);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            if (!observers.hasNext() && oneHand == null) {
                oneHand = (LadgtOneHand) player.defaultDiscard();
            }
            if (oneHand != null && checkOneHand(oneHand)) {
                noticeOneHand(oneHand);
                break;
            }
        }
        observers.markRound();
        index.getAndIncrement();
    }

    @Override
    public void score() {
        LadgtRoleEnum winRole = null;
        for (LadgtPlayObserver playObserver : observers) {
            if (playObserver.isEnd()) {
                winRole = playObserver.getRole();
            }
        }
        List<PlayScore> scores = new ArrayList<>();
        for (LadgtPlayObserver playObserver : observers) {
            scores.add(playObserver.calcScore(winRole));
        }
        for (LadgtPlayObserver playObserver : observers) {
            playObserver.sendScore(scores);
        }
        finish();
    }

    @Override
    public void setReceipt(NoticeMessage noticeMessage) {
        this.result.completed(noticeMessage);
    }

    private void finish() {
        // round finish to recycling and releasing resources
    }

    private void noticeOneHand(LadgtOneHand oneHand) {
        this.lastHand = oneHand;
        for (IPlayObserver observer : observers) {
            observer.notice(oneHand);
        }
    }

    /**
     * 验证是否符合出牌条件
     * @param oneHand
     */
    private boolean checkOneHand(LadgtOneHand oneHand) {
        if (lastHand == null) {
            return true;
        }
        if (lastHand.getPlayerId() == oneHand.getPlayerId()) {
            return true;
        }
        if (oneHand.compareTo(lastHand) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 构建一手牌
     * @param cards
     * @param player
     * @return
     */
    private LadgtOneHand getOneHand(ListType<LadgtCard> cards, IPlayObserver player) {
        if (CollectionUtils.isEmpty(cards)) {
            return null;
        }
        List<Integer> cardIndexes = new ArrayList<>();
        for (LadgtCard card : cards) {
            cardIndexes.add(card.getIndex());
        }
        LadgtOneHand ladgtOneHand = new LadgtOneHand(player.getCards(cardIndexes), configuration, player.getPlayer().getId());
        player.removeDiscard(ladgtOneHand);
        return ladgtOneHand;
    }


    private Integer dealResult(Future<NoticeMessage> result, IPlayObserver player, int timeOut) {
        try {
            NoticeMessage message = result.get(timeOut, TimeUnit.SECONDS);
            if (message.getType() == LadgtCommandTypeEnum.CALL_HELPER) {
                CallHelper callHelper = (CallHelper) message.getData();
                if (callHelper.getPokeCard() != null) {
                    boolean value = player.checkCallHelper(callHelper.getPokeCard().getIndex());
                    if (value) {
                        return callHelper.getPokeCard().getIndex();
                    }
                }
            }
        } catch (InterruptedException | ExecutionException | TimeoutException | ContextException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<LadgtPlayObserver> getPlayers() {
        return observers;
    }
}
