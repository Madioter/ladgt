/**
 * @Title: LadgtHandCards.java
 * @Package com.madiot.poke.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/22
 * @version
 */
package com.madiot.poke.context.model;

import com.madiot.poke.context.exception.ContextException;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: LadgtHandCards
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/22
 */
public class LadgtHandCards extends ArrayList<LadgtPokeCard> {

    public List<LadgtPokeCard> getCards(List<Integer> cardIndexes) {
        List<LadgtPokeCard> list = new ArrayList<>();
        loop:
        for (Integer index : cardIndexes) {
            LadgtPokeCard pokeCard = LadgtPokeCard.getInstance(index);
            for (LadgtPokeCard local : this) {
                if (pokeCard.equals(local) && list.contains(local)) {
                    list.add(local);
                    continue loop;
                }
            }
            throw new ContextException("can't have enough card to get");
        }
        return list;
    }
}
