/**
 * @Title: NormalBomb.java
 * @Package com.madiot.poke.ladgt.rule.poketype.type
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype.type;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;

import java.util.List;

/**
 * @ClassName: NormalBomb
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class NormalBomb extends BombType {

    @Override
    public int getValue(LadgtOneHand oneHand) {
        List<LadgtPokeCard> array = oneHand.getCards();
        return oneHand.getMaxCardValue().ordinal() + array.size() * 100;
    }

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        IPokeCard.ICardValue maxCardValue = null;
        if (array.size() < 4) {
            return false;
        }
        for (T pokeCard : array) {
            if (maxCardValue == null) {
                maxCardValue = pokeCard.getValue();
            } else if (maxCardValue != pokeCard.getValue()) {
                return false;
            }
        }
        oneHand.setMaxValue(maxCardValue);
        return true;
    }
}
