/**
 * @Title: JokerBomb.java
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
 * @ClassName: JokerBomb
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class JokerBomb extends BombType {

    @Override
    public int getValue(LadgtOneHand oneHand) {
        List<LadgtPokeCard> array = oneHand.getCards();
        if (array.size() == 3 && array.get(0).getType() == LadgtPokeCard.CardType.BLACK) {
            return 5 * 100 + oneHand.getMaxCardValue().ordinal();
        } else if (array.size() == 3 && array.get(0).getType() == LadgtPokeCard.CardType.RED) {
            return 6 * 100 + oneHand.getMaxCardValue().ordinal();
        } else if (array.size() == 4) {
            return 7 * 100 + oneHand.getMaxCardValue().ordinal();
        } else if (array.size() == 5) {
            return 8 * 100 + oneHand.getMaxCardValue().ordinal();
        } else if (array.size() == 6) {
            return 12 * 100 + oneHand.getMaxCardValue().ordinal();
        }
        return 0;
    }

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        if (array.size() < 3) {
            return false;
        } else if (array.size() == 3) {
            IPokeCard pokeCard = null;
            for (T temp : array) {
                if (pokeCard == null) {
                    pokeCard = temp;
                } else if (!pokeCard.equals(temp)) {
                    return false;
                }
            }
        } else {
            for (T temp : array) {
                if (temp.getValue() != LadgtPokeCard.CardValue.JOKER) {
                    return false;
                }
            }
        }
        oneHand.setMaxValue(LadgtPokeCard.CardValue.JOKER);
        return true;
    }
}
