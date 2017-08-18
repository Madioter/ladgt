package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.exception.ParserException;

import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/5/22.
 */
public class JokerBombCardType extends BombCardType {

    private int count;

    public JokerBombCardType(List<PokerCard> cards) {
        super(cards);
        check(cards);
        this.count = cards.size();
    }

    private void check(List<PokerCard> cards) {
        if (cards.size() < 3) {
            throw new ParserException("牌型不正确");
        }
        Map<PokerValueEnum, PokerData> map = arrange(cards);
        if (cards.size() == 3) {
            if (map.size() > 1) {
                throw new ParserException("牌型不正确");
            }
        } else {
            for (Map.Entry<PokerValueEnum, CardType.PokerData> entry : map.entrySet()) {
                if (entry.getKey() != PokerValueEnum.RED_JOKER || entry.getKey() != PokerValueEnum.BLACK_JOKER) {
                    throw new ParserException("牌型不正确");
                }
            }
        }
    }

    /**
     * 6王 > 12个2
     * 5王 > 8个2
     * 4王 > 7个2
     * 3大王 > 6个2
     * 3小王 > 5个2
     *
     * @return
     */
    @Override
    public int getIndex() {
        if (count == 3) {
            if (super.card.getValue() == PokerValueEnum.BLACK_JOKER) {
                return 500 + getIndex(super.card.getValue());
            } else {
                return 600 + getIndex(super.card.getValue());
            }
        } else if (count == 4) {
            return 700 + getIndex(super.card.getValue());
        } else if (count == 5) {
            return 800 + getIndex(super.card.getValue());
        } else if (count == 6) {
            return 1200 + getIndex(super.card.getValue());
        }
        throw new ParserException("牌型不正确");
    }

    @Override
    protected PokerCard getCard(List<PokerCard> cards) {
        return cards.get(0);
    }
}
