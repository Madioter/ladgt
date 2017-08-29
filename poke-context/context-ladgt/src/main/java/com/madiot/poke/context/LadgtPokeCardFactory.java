/**
 * @Title: PokeCardPool.java
 * @Package com.madiot.poke.rule.landegot
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.context;

import com.madiot.poke.context.api.IDistributional;
import com.madiot.poke.context.api.IPokeCardFactory;
import com.madiot.poke.context.config.IConfiguration;
import com.madiot.poke.ladgt.rule.pool.LadgtDeckPoke;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PokeCardPool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtPokeCardFactory implements IPokeCardFactory {

    private final List<LadgtPokeCard> deckPoke;
    private final IConfiguration configuration;

    public LadgtPokeCardFactory(IConfiguration configuration) {
        this.configuration = configuration;
        this.deckPoke = new ArrayList<>();
        this.deckPoke.addAll(new LadgtDeckPoke(true));
        this.deckPoke.addAll(new LadgtDeckPoke(false));
        this.deckPoke.addAll(new LadgtDeckPoke(false));
    }

    public void distributional(IDistributional distributional) {
        int landlordIndex = distributional.landlordIndex(deckPoke.size());
        while (deckPoke.get(landlordIndex).getType() == LadgtPokeCard.CardType.TAGGED) {
            landlordIndex = distributional.landlordIndex(deckPoke.size());
        }
        Integer nextIndex = distributional.getNextIndex(deckPoke.size());
        while (nextIndex != null) {
            if (landlordIndex == nextIndex) {
                distributional.setLandLord(deckPoke.get(nextIndex));
            }
            distributional.addCard(deckPoke.get(nextIndex));
            nextIndex = distributional.getNextIndex(deckPoke.size());
        }
    }
}
