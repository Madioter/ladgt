package com.madiot.poker.doudizhu.liuan.validate;

import com.madioter.poker.common.PokerDO;
import com.madioter.poker.common.PokerTypeEnum;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.doudizhu.liuan.domain.PlayerCardDO;
import com.madioter.poker.netty.message.common.enums.PokerCard;
import com.madioter.poker.netty.message.type.impl.DataListType;

import java.util.Iterator;
import java.util.List;

/**
 * Created by julian on 2017/5/16.
 */
public class DoudizhuValidate {

    /**
     * 出牌验证，验证当前玩家是否有可出的牌
     *
     * @param playerCardList 当前有的牌列表
     * @param cards          出牌列表
     * @return 是否符合规则
     */
    public static boolean checkCards(List<PlayerCardDO> playerCardList, DataListType<PokerCard> cards) {
        loop:
        for (PokerCard pokerCard : cards.getDataList()) {
            Iterator<PlayerCardDO> playerCardIterator = playerCardList.iterator();
            while (playerCardIterator.hasNext()) {
                PlayerCardDO playerCard = playerCardIterator.next();
                if (playerCard.getPokerId() == PokerDO.getIndex(pokerCard.getPokerType().getEnum(),
                        pokerCard.getPokerValue().getEnum())) {
                    playerCardIterator.remove();
                    // 每次匹配成功都会跳出
                    continue loop;
                }
            }
            // 只有匹配不上的这里返回失败
            return false;
        }
        // 全部匹配成功返回true
        return true;
    }

    /**
     * 牌比较大小
     *
     * @param current  当前出的牌
     * @param original 上一把的牌
     * @return 是否大于的规则
     */
    public static boolean checkGreater(DataListType<PokerCard> current, DataListType<PokerCard> original) {
        // 上一圈都不要，最大的人重新出牌
        if (original == null) {
            return true;
        }
        // 单张牌比大小
        if (original.getCount().getInt() == 1) {
            if (current.getCount().getInt() == 1) {
                return current.getDataList().get(0).getPokerValue().getEnum().getCode()
                        > original.getDataList().get(0).getPokerValue().getEnum().getCode();
            } else if (current.getCount().getInt() >= 4) {
                int value = current.getDataList().get(0).getPokerValue().getEnum().getCode();
                for (PokerCard card:current.getDataList()) {
                    if (card.getPokerValue().getEnum() == PokerValueEnum.BLACK_JOKER) {

                    }
                }
            }

        }
    }
}
