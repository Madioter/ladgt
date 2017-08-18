/**
 * @Title: LadgtPokeRound.java
 * @Package com.madiot.poke.ladgt.play.round
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.play.round;

import com.madiot.poke.api.play.IDistributional;
import com.madiot.poke.api.play.IPlayRound;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.ladgt.play.observer.LadgtPlayObserver;
import com.madiot.poke.ladgt.rule.pool.LadgtDeckPoke;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCardPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: LadgtPokeRound
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtPlayRound implements IPlayRound {

    private final List<LadgtPlayObserver> observers;
    private LadgtPokeCardPool ladgtPokeCardPool;
    private LadgtOneHand lastHand;
    private IPokeCard taggedCard = LadgtDeckPoke.TAGGED_EIGHT;

    public LadgtPlayRound(LadgtPokeCardPool ladgtPokeCardPool, List<LadgtPlayObserver> observers) {
        this.ladgtPokeCardPool = ladgtPokeCardPool;
        this.observers = observers;
    }

    public LadgtOneHand getLastHand() {
        return lastHand;
    }

    public void setLastHand(LadgtOneHand lastHand) {
        this.lastHand = lastHand;
    }

    public void start() {
        IDistributional distributional = new LadgtDistributional();
        ladgtPokeCardPool.distributional(distributional);
        int i;
        for (i = 0; i < observers.size(); i++) {
            observers.get(i).deal(distributional.getNextList(i));
            if (observers.get(i).isHaveTaggedCard()) {
                observers.get(i).addCards(distributional.getNextList(Integer.MAX_VALUE));
            }
        }
    }

    private List<IPokeCard> randomCards() {
        return new ArrayList<>();
    }

    @Override
    public void end() {

    }

    @Override
    public List<LadgtPlayObserver> getPlayers() {
        return observers;
    }
}
