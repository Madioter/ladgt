package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.*;
import com.madioter.poker.common.exception.ParserException;

import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/5/20.
 */
public class BombCardType extends CardType {

    private int count;

    protected PokerCard card;

    public BombCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
    }

    protected PokerCard getCard(List<PokerCard> cards) {
        Map<PokerValueEnum, PokerData> map = arrange(cards);
        if (map.size() == 1) {
            for (Map.Entry<PokerValueEnum, CardType.PokerData> entry : map.entrySet()) {
                this.count = entry.getValue().getCount();
                if (this.count >= 4){
                    return entry.getValue().getCard();
                }
            }
        }
        throw new ParserException("牌型不正确");
    }

    @Override
    public int getIndex() {
        return count * 100 + getIndex(this.card.getValue());
    }

    @Override
    public int compareTo(CardType o) {
        if (o == null) {
            return 1;
        }
        if (o instanceof BombCardType) {
            if (getIndex() > o.getIndex()) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }

        return -1;
    }
}
