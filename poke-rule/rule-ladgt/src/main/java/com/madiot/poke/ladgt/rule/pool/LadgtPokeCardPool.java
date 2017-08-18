/**
 * @Title: PokeCardPool.java
 * @Package com.madiot.poke.rule.landegot
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.rule.pool;

import com.madiot.poke.api.play.IDistributional;
import com.madiot.poke.api.rule.IPokeCardFactory;
import com.madiot.poke.ladgt.rule.config.LadgtConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PokeCardPool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtPokeCardPool implements IPokeCardFactory {

    private final List<LadgtPokeCard> deckPoke;
    private final LadgtConfiguration configuration;

    public LadgtPokeCardPool(LadgtConfiguration configuration) {
        this.configuration = configuration;
        this.deckPoke = new ArrayList<>();
        this.deckPoke.addAll(new LadgtDeckPoke(true));
        this.deckPoke.addAll(new LadgtDeckPoke(false));
        this.deckPoke.addAll(new LadgtDeckPoke(false));
    }

    public void distributional(IDistributional distributional) {
        while (distributional.getNextIndex() != null) {
            distributional.addCard(deckPoke.get(distributional.getNextIndex()));
        }
    }
}
