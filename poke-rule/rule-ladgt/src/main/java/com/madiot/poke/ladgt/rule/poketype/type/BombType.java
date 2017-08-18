/**
 * @Title: BombType.java
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
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;

/**
 * @ClassName: BombType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class BombType extends BasePokeTypeRule {

    private static NormalBomb normalBomb = new NormalBomb();
    private static JokerBomb jokerBomb = new JokerBomb();
    private static TaggedBomb taggedBomb = new TaggedBomb();

    @Override
    public String getType() {
        return PokeType.BOMB.toString();
    }

    @Override
    public <T extends IPokeCard> boolean check(IOneHand<T> oneHand) {
        if (normalBomb.check(oneHand)) {
            return true;
        } else if (jokerBomb.check(oneHand)) {
            return true;
        } else if (taggedBomb.check(oneHand)) {
            return true;
        }
        return false;
    }

    public int getValue(LadgtOneHand oneHand) {
        if (taggedBomb.check(oneHand)) {
            return taggedBomb.getValue(oneHand);
        } else if (jokerBomb.check(oneHand)) {
            return jokerBomb.getValue(oneHand);
        } else if (normalBomb.check(oneHand)) {
            return normalBomb.getValue(oneHand);
        }
        return 0;
    }
}
