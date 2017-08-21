/**
 * @Title: IPokeObserver.java
 * @Package com.madiot.poke.rule.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context.api;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @ClassName: IPokeObserver
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPlayObserver {

    void notice(IOneHand oneHand);

    void play(IOneHand oneHand);

    void pass();

    void deal(List<IPokeCard> pokeCards);

    //Future<INoticeMessage> ready();

}
