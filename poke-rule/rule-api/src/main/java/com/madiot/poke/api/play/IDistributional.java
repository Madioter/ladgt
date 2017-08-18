/**
 * @Title: IDistributional.java
 * @Package com.madiot.poke.api.play
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.api.play;

/**
 * @ClassName: IDistributional
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */

import com.madiot.poke.api.rule.IPokeCard;

import java.util.List;

public interface IDistributional<T extends IPokeCard> {

    void addCard(T pokeCard);

    Integer getNextIndex();

    public List<T> getNextList(int index);
}
