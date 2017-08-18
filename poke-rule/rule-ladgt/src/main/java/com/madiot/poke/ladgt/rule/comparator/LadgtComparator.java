/**
 * @Title: ladgtComparator.java
 * @Package com.madiot.poke.rule.ladgt.comparator
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.comparator;

import com.madiot.poke.api.rule.IPokeTypeComparator;
import com.madiot.poke.errors.ComparatorException;
import com.madiot.poke.ladgt.rule.poketype.BasePokeTypeRule;
import com.madiot.poke.ladgt.rule.poketype.type.BombType;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;

/**
 * @ClassName: ladgtComparator
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtComparator implements IPokeTypeComparator<LadgtOneHand> {

    public int compareWith(LadgtOneHand target, LadgtOneHand source) throws ComparatorException {
        if (isBomb(target) && !isBomb(source)) {
            return 1;
        } else if (!isBomb(target) && target.getPokeType().equals(source.getPokeType())) {
            if (source.getMaxCardValue().lessThen(target.getMaxCardValue()) && source.getCards().size() == target.getCards().size()) {
                return 1;
            }
        } else if (isBomb(target) && isBomb(source)) {
            BombType bombType = (BombType) target.getConfiguration().getPokeTypeRegistry().getType(BasePokeTypeRule.PokeType.BOMB.toString());
            return bombType.getValue(target) - bombType.getValue(source);
        }
        return -1;
    }

    private boolean isBomb(LadgtOneHand target) {
        return target.getPokeType().equals(BasePokeTypeRule.PokeType.BOMB.name());
    }
}
