/**
 * @Title: OneHand.java
 * @Package com.madiot.poke.rule.ladgt.pool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.pool;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.api.rule.IPokeTypeRule;
import com.madiot.poke.context.config.IConfiguration;
import com.madiot.poke.errors.PokeRuleException;

import java.util.List;

/**
 * @ClassName: OneHand
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtOneHand implements Comparable<LadgtOneHand>, IOneHand<LadgtPokeCard> {

    private String pokeType;

    private LadgtPokeCard.CardValue maxCardValue;

    private final List<LadgtPokeCard> cards;

    private IConfiguration configuration;

    public LadgtOneHand(List<LadgtPokeCard> cards, IConfiguration configuration) {
        this.cards = cards;
        this.configuration = configuration;
        IPokeTypeRule rule = configuration.getPokeTypeRegistry().getType(this);
        if (rule == null) {
            throw new PokeRuleException("can't find rule for [" + cards + "]");
        }
        this.pokeType = rule.getType();
    }

    @Override
    public int compareTo(LadgtOneHand source) {
        return configuration.getComparator().compareWith(this, source);
    }


    @Override
    public void setMaxValue(IPokeCard.ICardValue maxCardValue) {
        if (maxCardValue instanceof LadgtPokeCard.CardValue) {
            this.maxCardValue = (LadgtPokeCard.CardValue) maxCardValue;
        }
    }

    @Override
    public List<LadgtPokeCard> getCards() {
        return this.cards;
    }

    public String getPokeType() {
        return pokeType;
    }

    public LadgtPokeCard.CardValue getMaxCardValue() {
        return maxCardValue;
    }

    public IConfiguration getConfiguration() {
        return configuration;
    }
}
