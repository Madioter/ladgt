/**
 * @Title: LadgtPokeObserver.java
 * @Package com.madiot.poke.rule.ladgt.pool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.play.observer;

import com.madiot.poke.api.play.INoticeMessage;
import com.madiot.poke.api.play.IPlayListener;
import com.madiot.poke.api.play.IPlayObserver;
import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.errors.PokeContainsException;
import com.madiot.poke.ladgt.rule.pool.LadgtDeckPoke;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCardFactory;
import com.madioter.poker.common.future.CallbackFuture;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @ClassName: LadgtPokeObserver
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtPlayObserver implements IPlayObserver {

    private List<IPokeCard> pokeCards;

    private final List<IPlayListener> playListeners;

    private final LadgtPokeCardFactory ladgtPokeCardFactory;

    private boolean haveTaggedCard = false;

    private CallbackFuture<INoticeMessage> callbackFuture;

    public LadgtPlayObserver(List<IPlayListener> playListeners, LadgtPokeCardFactory ladgtPokeCardFactory) {
        this.ladgtPokeCardFactory = ladgtPokeCardFactory;
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
    public void deal(List<IPokeCard> pokeCards) {
        this.pokeCards = pokeCards;
        if (this.pokeCards.contains(LadgtDeckPoke.TAGGED_EIGHT)) {
            this.haveTaggedCard = true;
        }
    }

    @Override
    public Future<INoticeMessage> ready() {
        this.callbackFuture = new CallbackFuture<>();
        return this.callbackFuture;
    }

    private boolean checkContains(IOneHand oneHand) {
        return pokeCards.containsAll(oneHand.getCards());
    }

    public void remove(IOneHand oneHand) {
        pokeCards.removeAll(oneHand.getCards());
    }

    public LadgtPokeCardFactory getLadgtPokeCardFactory() {
        return ladgtPokeCardFactory;
    }

    public boolean isHaveTaggedCard() {
        return haveTaggedCard;
    }

    public void addCards(List<IPokeCard> nextList) {
        this.pokeCards.addAll(nextList);
    }
}
