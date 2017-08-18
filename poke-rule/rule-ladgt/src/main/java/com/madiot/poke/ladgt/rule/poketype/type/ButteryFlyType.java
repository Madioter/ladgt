/**
 * @Title: ButteryflyType.java
 * @Package com.madiot.poke.ladgt.rule.poketype.type
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype.type;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.ladgt.rule.poketype.BasePokeTypeRule;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ButteryflyType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class ButteryFlyType extends BasePokeTypeRule {


    @Override
    public String getType() {
        return PokeType.BUTTERFLY.toString();
    }

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        if (CollectionUtils.isEmpty(array) || array.size() % 5 != 0) {
            return false;
        } else {
            Map<IPokeCard.ICardValue, Integer> temp = new HashMap<>();
            for (IPokeCard pokeCard : array) {
                if (temp.containsKey(pokeCard.getValue())) {
                    temp.put(pokeCard.getValue(), temp.get(pokeCard.getValue()) + 1);
                } else {
                    temp.put(pokeCard.getValue(), 1);
                }
            }
            int count = 0;
            IPokeCard.ICardValue maxCardValue = null;
            IPokeCard.ICardValue maxTwoCardValue = null;
            for (Map.Entry<IPokeCard.ICardValue, Integer> entry : temp.entrySet()) {
                if (entry.getValue() == 3) {
                    count++;
                    if (maxCardValue == null || maxCardValue.lessThen(entry.getKey())) {
                        maxCardValue = entry.getKey();
                    }
                } else if (entry.getValue() == 5) {
                    count++;
                    if (maxCardValue == null || maxCardValue.lessThen(entry.getKey())) {
                        maxCardValue = entry.getKey();
                    }
                    if (maxTwoCardValue == null || maxTwoCardValue.lessThen(entry.getKey())) {
                        maxTwoCardValue = entry.getKey();
                    }
                } else if (entry.getValue() == 2) {
                    if (maxTwoCardValue == null || maxTwoCardValue.lessThen(entry.getKey())) {
                        maxTwoCardValue = entry.getKey();
                    }
                } else {
                    return false;
                }
            }
            IPokeCard.ICardValue currentCardValue = maxCardValue;
            for (int i = 0; i < count; i++) {
                if (currentCardValue == null || !temp.containsKey(currentCardValue) || (temp.get(currentCardValue) != 3 && temp.get(currentCardValue) != 5)) {
                    return false;
                }
                currentCardValue = currentCardValue.before();
            }
            currentCardValue = maxTwoCardValue;
            for (int i = 0; i < count; i++) {
                if (currentCardValue == null || !temp.containsKey(currentCardValue) || (temp.get(currentCardValue) != 2 && temp.get(currentCardValue) != 5)) {
                    return false;
                }
                currentCardValue = currentCardValue.before();
            }
            oneHand.setMaxValue(maxCardValue);
            return true;
        }
    }
}
