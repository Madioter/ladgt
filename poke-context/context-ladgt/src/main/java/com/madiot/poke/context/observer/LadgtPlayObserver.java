/**
 * @Title: LadgtPlayObserver.java
 * @Package com.madiot.poke.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.context.observer;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCardFactory;
import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.LadgtCodecHandler;
import com.madiot.poke.context.api.IPlayListener;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayer;
import com.madiot.poke.context.config.IConfiguration;
import com.madiot.poke.errors.PokeContainsException;
import com.madiot.poke.ladgt.rule.pool.LadgtDeckPoke;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @ClassName: LadgtPlayObserver
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayObserver implements IPlayObserver<LadgtPokeCard> {

    private List<LadgtPokeCard> pokeCards;

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

    }

    @Override
    public void play(IOneHand oneHand) {
        doListener(oneHand, IPlayListener.ListenerOccasion.BEFORE_PLAY);
        if (checkContains(oneHand)) {
            remove(oneHand);
        } else {
            throw new PokeContainsException("not have enough poke cards");
        }
        doListener(oneHand, IPlayListener.ListenerOccasion.AFTER_PLAY);
    }

    private void doListener(IOneHand oneHand, IPlayListener.ListenerOccasion occasion) {
        for (IPlayListener playListener : playListeners) {
            if (playListener.getOccasionType() == occasion) {
                playListener.doListener(oneHand, this);
            }
        }
    }

    @Override
    public void pass() {

    }

    @Override
    public void deal(List<LadgtPokeCard> pokeCards, LadgtPokeCard landlordCard) {
        this.pokeCards = pokeCards;
        this.landlordCard = landlordCard;
        if (this.pokeCards.contains(LadgtDeckPoke.TAGGED_EIGHT)) {
            this.haveTaggedCard = true;
        }
    }

    @Override
    public void sendDealMessage() {
        byte[] message = LadgtCodecHandler.buildDealMessage(this);
        this.configuration.getPokeMessageServer().sendMessage(message);
    }

    public Future<NoticeMessage> sendCallHelperMessage() {
        if (this.role == LadgtRoleEnum.LAND_LORD_WITH_HELPER || this.role == LadgtRoleEnum.LAND_LORD) {
            byte[] callHelperMessage = LadgtCodecHandler.buildCallHelperMessage(this);
            return this.configuration.getPokeMessageServer().sendMessage(callHelperMessage);
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
    public void setHelperRole(int pokeCardIndex) {
        LadgtPokeCard pokeCard = LadgtPokeCard.getInstance(pokeCardIndex);
        if (pokeCards.contains(pokeCard) && role != LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
            this.role = LadgtRoleEnum.HELPER;
        } else if (pokeCards.contains(pokeCard) && role == LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
            this.role = LadgtRoleEnum.LAND_LORD;
        }
    }

    private boolean checkContains(IOneHand oneHand) {
        return pokeCards.containsAll(oneHand.getCards());
    }

    public void remove(IOneHand oneHand) {
        pokeCards.removeAll(oneHand.getCards());
    }

    public IPokeCardFactory getPokeCardFactory() {
        return configuration.getPokeCardFactory();
    }

    public boolean isHaveTaggedCard() {
        return haveTaggedCard;
    }

    public void addCards(List<LadgtPokeCard> nextList) {
        this.pokeCards.addAll(nextList);
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

    public IPlayer getPlayer() {
        return this.player;
    }
}
