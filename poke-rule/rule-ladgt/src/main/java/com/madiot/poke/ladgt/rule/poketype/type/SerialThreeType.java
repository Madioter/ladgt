/**
 * @Title: SerialThreeType.java
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
 * @ClassName: SerialThreeType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class SerialThreeType extends BasePokeTypeRule {

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        if (CollectionUtils.isEmpty(array) || array.size() % 3 != 0) {
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
            for (Map.Entry<IPokeCard.ICardValue, Integer> entry : temp.entrySet()) {
                if (entry.getValue() != 3) {
                    return false;
                }
                if (maxCardValue == null || maxCardValue.lessThen(entry.getKey())) {
                    maxCardValue = entry.getKey();
                }
                count++;
            }
            IPokeCard.ICardValue currentCardValue = maxCardValue;
            for (int i = 0; i < count; i++) {
                if (currentCardValue == null || !temp.containsKey(currentCardValue)) {
                    return false;
                }
                currentCardValue = currentCardValue.before();
            }
            oneHand.setMaxValue(maxCardValue);
            return true;
        }
    }

    @Override
    public String getType() {
        return PokeType.SERIAL_THREE.toString();
    }
}
