/**
 * @Title: PokeTypeHandler.java
 * @Package com.madiot.poke.rule.landegot.poketype
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.play.round;

import com.madiot.poke.api.play.IPlayHandler;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCardFactory;

/**
 * @ClassName: PokeTypeHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtPokeTypeHandler implements IPlayHandler {

    private LadgtOneHand oneHand;

    private LadgtPokeCardFactory pokeCardPool;

    public LadgtPokeTypeHandler(LadgtOneHand oneHand, LadgtPokeCardFactory pokeCardPool) {
        this.oneHand = oneHand;
        this.pokeCardPool = pokeCardPool;
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deserialize(byte[] data) {

    }


    /*public boolean compareCheck() throws ComparatorException {
        int result = this.oneHand.compareTo(pokeCardPool.getLastHand());
        if (result > 0) {
            pokeCardPool.setLastHand(this.oneHand);
            return true;
        }
        throw new ComparatorException("one hand Less than equal to last hand");
    }*/
}
