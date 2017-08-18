/**
 * @Title: TaggedBomb.java
 * @Package com.madiot.poke.ladgt.rule.poketype.type
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype.type;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.ladgt.rule.pool.LadgtDeckPoke;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @ClassName: TaggedBomb
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class TaggedBomb extends BombType {
    @Override
    public int getValue(LadgtOneHand oneHand) {
        return Integer.MAX_VALUE;
    }

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        List<T> array = oneHand.getCards();
        if (CollectionUtils.isNotEmpty(array) && array.size() == 1 && array.get(0).equals(LadgtDeckPoke.TAGGED_EIGHT)) {
            return true;
        }
        oneHand.setMaxValue(array.get(0).getValue());
        return false;
    }
}
