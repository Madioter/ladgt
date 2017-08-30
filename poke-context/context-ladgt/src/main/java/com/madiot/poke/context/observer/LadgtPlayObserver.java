/**
 * @Title: LadgtPlayObserver.java
 * @Package com.madiot.poke.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.context.observer;

import com.madiot.common.utils.collection.CollectionUtil;
import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;
import com.madiot.poke.codec.message.NoticeHead;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.LadgtCodecHandler;
import com.madiot.poke.context.api.IPlayListener;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poker.common.domain.IPlayer;
import com.madiot.poke.context.config.IConfiguration;
import com.madiot.poke.context.exception.ContextException;
import com.madiot.poke.context.model.LadgtHandCards;
import com.madiot.poke.context.model.NoticeMessageFuture;
import com.madiot.poke.context.model.PlayScore;
import com.madiot.poke.ladgt.rule.pool.LadgtDeckPoke;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import com.madiot.poker.common.future.CallbackFuture;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: LadgtPlayObserver
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayObserver implements IPlayObserver<LadgtPokeCard> {

    private LadgtHandCards pokeCards = new LadgtHandCards();

    private final List<IPlayListener> playListeners;

    private boolean haveTaggedCard = false;

    private LadgtPokeCard landlordCard;

    private IConfiguration configuration;

    private IPlayer player;

    //private CallbackFuture<INoticeMessage> callbackFuture;

    private LadgtRoleEnum role;

    public LadgtPlayObserver(IPlayer player, List<IPlayListener> playListeners, IConfiguration configuration) {
        this.player = player;
        this.configuration = configuration;
        if (CollectionUtils.isNotEmpty(playListeners)) {
            Collections.sort(playListeners, new Comparator<IPlayListener>() {
                @Override
                public int compare(IPlayListener o1, IPlayListener o2) {
                    return o1.priority() - o2.priority();
                }
            });
            this.playListeners = playListeners;
        } else {
            this.playListeners = new ArrayList<>();
        }
    }

    @Override
    public void notice(IOneHand oneHand) {
        if (this.player.getId() != oneHand.getPlayerId()) {
            byte[] message = LadgtCodecHandler.buildNoticeDiscardMessage(this, (LadgtOneHand) oneHand);
            this.configuration.getPokeMessageServer().sendMessage(message, this.player.getServerIp(), this.player.getId());
        }
    }

    @Override
    public CallbackFuture<NoticeMessage> discard(IOneHand lastOneHand) {
        byte[] message;
        if (lastOneHand != null && lastOneHand.getPlayerId() == this.player.getId()) {
            message = LadgtCodecHandler.buildDiscardMessage(this, null);
        } else {
            message = LadgtCodecHandler.buildDiscardMessage(this, (LadgtOneHand) lastOneHand);
        }
        this.configuration.getPokeMessageServer().sendMessage(message, this.player.getServerIp(), this.player.getId());
        return new NoticeMessageFuture(NoticeHead.getId(message));
    }

    private void doListener(IOneHand oneHand, IPlayListener.ListenerOccasion occasion) {
        for (IPlayListener playListener : playListeners) {
            if (playListener.getOccasionType() == occasion) {
                playListener.doListener(oneHand, this);
            }
        }
    }

    private boolean isLandloard() {
        return this.role == LadgtRoleEnum.LAND_LORD || this.role == LadgtRoleEnum.LAND_LORD_WITH_HELPER;
    }

    @Override
    public void pass() {

    }

    @Override
    public void deal(List<LadgtPokeCard> pokeCards, LadgtPokeCard landlordCard) {
        this.pokeCards.addAll(pokeCards);
        this.landlordCard = landlordCard;
        if (this.pokeCards.contains(LadgtDeckPoke.TAGGED_EIGHT)) {
            this.haveTaggedCard = true;
        }
    }

    @Override
    public void sendDealMessage(IPlayer landlordPlayer) {
        byte[] message = LadgtCodecHandler.buildDealMessage(this, landlordPlayer);
        this.configuration.getPokeMessageServer().sendMessage(message, this.player.getServerIp(), this.player.getId());
    }

    public CallbackFuture<NoticeMessage> sendCallHelperMessage() {
        if (this.role == LadgtRoleEnum.LAND_LORD_WITH_HELPER || this.role == LadgtRoleEnum.LAND_LORD) {
            byte[] callHelperMessage = LadgtCodecHandler.buildCallHelperMessage(this);
            this.configuration.getPokeMessageServer().sendMessage(callHelperMessage, this.player.getServerIp(), this.player.getId());
            return new NoticeMessageFuture(NoticeHead.getId(callHelperMessage));
        }
        return null;
    }

    @Override
    public boolean checkCallHelper(int pokeCardIndex) {
        LadgtPokeCard pokeCard = LadgtPokeCard.getInstance(pokeCardIndex);
        if (this.role == LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
            int count = 0;
            for (LadgtPokeCard card : pokeCards) {
                if (pokeCard.equals(card)) {
                    count++;
                }
            }
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IOneHand defaultDiscard() {
        if (this.pokeCards.size() > 0) {
            List<LadgtPokeCard> cards = new ArrayList<>();
            cards.add(this.pokeCards.get(0));
            return new LadgtOneHand(cards, configuration, this.player.getId());
        }
        throw new ContextException("don't have enough poke to discard");
    }

    @Override
    public void removeDiscard(IOneHand oneHand) {
        this.pokeCards.removeAll(oneHand.getCards());
    }

    @Override
    public List<LadgtPokeCard> getCards(List<Integer> cards) {
        return this.pokeCards.getCards(cards);
    }

    @Override
    public boolean isEnd() {
        if (CollectionUtil.isEmpty(this.pokeCards)) {
            return true;
        }
        return false;
    }

    @Override
    public void setHelperRole(int pokeCardIndex) {
        LadgtPokeCard pokeCard = LadgtPokeCard.getInstance(pokeCardIndex);
        if (pokeCards.contains(pokeCard) && role != LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
            this.role = LadgtRoleEnum.HELPER;
        } else if (pokeCards.contains(pokeCard) && role == LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
            this.role = LadgtRoleEnum.LAND_LORD;
        }
        byte[] helperNoticeMessage = LadgtCodecHandler.buildHelperNoticeMessage(this, pokeCard);
        this.configuration.getPokeMessageServer().sendMessage(helperNoticeMessage, this.player.getServerIp(), this.player.getId());
    }

    private boolean checkContains(IOneHand oneHand) {
        return this.pokeCards.containsAll(oneHand.getCards());
    }

    public boolean isHaveTaggedCard() {
        return haveTaggedCard;
    }

    public void addCards(List<LadgtPokeCard> nextList) {
        this.pokeCards.addAll(nextList);
        Collections.sort(this.pokeCards, new Comparator<LadgtPokeCard>() {
            @Override
            public int compare(LadgtPokeCard o1, LadgtPokeCard o2) {
                return o1.getValue().compareWith(o2.getValue());
            }
        });
    }

    public LadgtRoleEnum getRole() {
        return role;
    }

    public void setRole(LadgtRoleEnum role) {
        this.role = role;
    }

    public IConfiguration getConfiguraction() {
        return this.configuration;
    }

    public List<LadgtPokeCard> getPokeCards() {
        return pokeCards;
    }

    public LadgtPokeCard getLandlordCard() {
        return this.landlordCard;
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    public PlayScore calcScore(LadgtRoleEnum winRole) {
        int score = configuration.getScoreRule().getScore(this.role, winRole);
        this.player.calcScore(score);
        return new PlayScore(this.player, score, score > 0);
    }

    public void sendScore(List<PlayScore> scores) {
        byte[] scoreMessage = LadgtCodecHandler.buildScoreMessage(this, scores);
        this.configuration.getPokeMessageServer().sendMessage(scoreMessage, this.player.getServerIp(), this.player.getId());
    }
}
