/**
 * @Title: IOneHand.java
 * @Package com.madiot.poke.rule.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.api.rule;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: IOneHand
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IOneHand<T> {

    void setMaxValue(IPokeCard.ICardValue maxCardValue);

    List<T> getCards();

    String getPokeType();
}
