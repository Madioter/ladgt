/**
 * @Title: SingleCard.java
 * @Package com.madiot.poke.rule.landegot.poketype
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype.type;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.ladgt.rule.poketype.BasePokeTypeRule;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @ClassName: SingleCard
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class SingleCard extends BasePokeTypeRule {

    @Override
    public String getType() {
        return PokeType.SINGLE.toString();
    }

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        if (CollectionUtils.isNotEmpty(array) && array.size() == 1) {
            oneHand.setMaxValue(array.get(0).getValue());
            return true;
        }
        return false;
    }
}
