package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.exception.ParserException;

import java.util.List;

/**
 * Created by julian on 2017/5/20.
 */
public class SimpleCardType extends CardType {

    private PokerCard card;

    private int index;

    public SimpleCardType(List<PokerCard> cards) {
        super(cards);
        this.card = cards.get(0);
        this.index = getIndex(card.getValue());
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(CardType o) {
        if (o == null) {
            return 1;
        }
        if (o instanceof BombCardType) {
            return -1;
        } else if (o instanceof SimpleCardType) {
            if (index > ((SimpleCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }

        return -1;
    }
}
