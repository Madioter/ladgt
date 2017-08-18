/**
 * @Title: PairType.java
 * @Package com.madiot.poke.ladgt.rule.poketype
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype.type;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.ladgt.rule.poketype.BasePokeTypeRule;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @ClassName: PairType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class PairType extends BasePokeTypeRule {

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        if (CollectionUtils.isEmpty(array) || array.size() != 2) {
            return false;
        }
        if (!array.get(0).getValue().equals(array.get(1).getValue())) {
            return false;
        }
        if (array.get(0).getValue() == LadgtPokeCard.CardValue.JOKER && !array.get(0).equals(array.get(1))) {
            return false;
        }
        oneHand.setMaxValue(array.get(0).getValue());
        return true;
    }

    @Override
    public String getType() {
        return PokeType.PAIR.toString();
    }
}
