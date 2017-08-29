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
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poker.common.future.CallbackFuture;

import java.util.List;

/**
 * @ClassName: IPokeObserver
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPlayObserver<T extends IPokeCard> {

    void notice(IOneHand oneHand);

    CallbackFuture<NoticeMessage> discard(IOneHand lastOneHand);

    void pass();

    IPlayer getPlayer();

    void deal(List<T> pokeCards, T landlordCard);

    void sendDealMessage(IPlayer landlordPlayer);

    CallbackFuture<NoticeMessage> sendCallHelperMessage();

    void setHelperRole(int pokeCardIndex);

    boolean checkCallHelper(int pokeCardIndex);

    /**
     * 获取默认牌
     */
    IOneHand defaultDiscard();

    void removeDiscard(IOneHand oneHand);

    List<T> getCards(List<Integer> cards);

    boolean isEnd();
}
