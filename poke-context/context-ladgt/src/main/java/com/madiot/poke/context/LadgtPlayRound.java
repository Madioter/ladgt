/**
 * @Title: LadgtPokeRound.java
 * @Package com.madiot.poke.ladgt.play.round
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context;

import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;
import com.madiot.poke.context.api.IDistributional;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.context.observer.LadgtPlayObserver;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCardFactory;

import java.util.List;

/**
 * @ClassName: LadgtPokeRound
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtPlayRound implements IPlayRound {

    private final List<LadgtPlayObserver> observers;
    private LadgtPokeCardFactory ladgtPokeCardFactory;
    private LadgtOneHand lastHand;

    public LadgtPlayRound(LadgtPokeCardFactory ladgtPokeCardFactory, List<LadgtPlayObserver> observers) {
        this.ladgtPokeCardFactory = ladgtPokeCardFactory;
        this.observers = observers;
    }

    public LadgtOneHand getLastHand() {
        return lastHand;
    }

    public void setLastHand(LadgtOneHand lastHand) {
        this.lastHand = lastHand;
    }

    public void deal() {
        IDistributional<LadgtPokeCard> distributional = new LadgtDistributional();
        ladgtPokeCardFactory.distributional(distributional);
        int index = distributional.getLandlordIndex();
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).deal(distributional.getNextList(i), distributional.getLandlordCard());
            if (observers.get(i).isHaveTaggedCard()) {
                observers.get(i).addCards(distributional.getNextList(Integer.MAX_VALUE));
            }
            if (i == index && observers.get(i).isHaveTaggedCard()) {
                observers.get(i).setRole(LadgtRoleEnum.LAND_LORD_WITH_HELPER);
            } else if (i == index) {
                observers.get(i).setRole(LadgtRoleEnum.LAND_LORD);
            } else if (observers.get(i).isHaveTaggedCard()) {
                observers.get(i).setRole(LadgtRoleEnum.HELPER);
            } else {
                observers.get(i).setRole(LadgtRoleEnum.FARMER);
            }
        }
    }

    @Override
    public void end() {

    }

    @Override
    public List<LadgtPlayObserver> getPlayers() {
        return observers;
    }
}
