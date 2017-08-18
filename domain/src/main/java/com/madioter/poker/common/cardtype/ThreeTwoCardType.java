package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.exception.ParserException;

import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/5/20.
 */
public class ThreeTwoCardType extends CardType {

    private int index;

    private PokerCard card;

    public ThreeTwoCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
        this.index = getIndex(this.card.getValue());
    }

    private PokerCard getCard(List<PokerCard> cards) {
        Map<PokerValueEnum, PokerData> map = arrange(cards);
        if (map.size() == 2) {
            for (Map.Entry<PokerValueEnum, CardType.PokerData> entry : map.entrySet()) {
                if (entry.getValue().getCount() == 3) {
                    return entry.getValue().getCard();
                }
            }
        }
        throw new ParserException("牌型不正确");
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public int compareTo(CardType o) {
        if (o == null) {
            return 1;
        }
        if (o instanceof BombCardType) {
            return -1;
        } else if (o instanceof ThreeTwoCardType) {
            if (index > ((ThreeTwoCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }
        return -1;
    }
}
