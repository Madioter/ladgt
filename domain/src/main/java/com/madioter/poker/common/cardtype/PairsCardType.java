package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.exception.ParserException;

import java.util.List;

/**
 * Created by julian on 2017/5/20.
 */
public class PairsCardType extends CardType {

    private PokerCard card;

    private Integer index;

    public PairsCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
        this.index = getIndex(card.getValue());
    }

    private PokerCard getCard(List<PokerCard> cards) {
        if (cards.size() != 2) {
            throw new ParserException("牌型不正确");
        }
        if (cards.get(0).getValue().equals(cards.get(1).getValue())) {
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
        } else if (o instanceof PairsCardType) {
            if (index > ((PairsCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }

        return -1;
    }
}
