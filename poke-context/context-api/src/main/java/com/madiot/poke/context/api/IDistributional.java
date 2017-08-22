/**
 * @Title: IDistributional.java
 * @Package com.madiot.poke.api.play
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context.api;

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

    Integer getNextIndex(int total);

    List<T> getNextList(int index);

    int landlordIndex(int total);

    void setLandLord(T pokeCard);

    public T getLandlordCard();

    public int getLandlordIndex();
}
