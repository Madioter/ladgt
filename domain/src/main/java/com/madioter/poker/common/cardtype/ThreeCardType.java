package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerTypeEnum;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.exception.ParserException;

import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/5/20.
 */
public class ThreeCardType extends CardType {

    private int index;

    private PokerCard card;

    public ThreeCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
        this.index = getIndex(this.card.getValue());
    }

    private PokerCard getCard(List<PokerCard> cards) {
        if (cards.size() != 3) {
            throw new ParserException("牌型不正确");
        }
        Map<PokerValueEnum, CardType.PokerData> map = arrange(cards);
        if (map.size() == 1) {
            return cards.get(0);
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
        } else if (o instanceof ThreeCardType) {
            if (index > ((ThreeCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }
        return -1;
    }
}
