package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.exception.ParserException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/5/20.
 */
public class SerialPairsCardType extends CardType {

    private int index;

    private PokerCard card;

    public SerialPairsCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
        this.index = getIndex(this.card.getValue());
    }

    private PokerCard getCard(List<PokerCard> cards) {
        Map<PokerValueEnum, PokerData> map = arrange(cards);
        List<Integer> pokerCardIndex = new ArrayList<>();
        for (Map.Entry<PokerValueEnum, CardType.PokerData> entry : map.entrySet()) {
            pokerCardIndex.add(getIndex(entry.getValue().getCard().getValue()));
            if (this.card == null || getIndex(this.card.getValue()) > getIndex(entry.getValue().getCard().getValue())) {
                this.card = entry.getValue().getCard();
            }
        }
        Collections.sort(pokerCardIndex);
        Integer current = null;
        for (Integer index : pokerCardIndex) {
            if (index == getIndex(PokerValueEnum.V_2)) {
                throw new ParserException("牌型不正确");
            }
            if (current == null || (current + 1) == index) {
                current = index;
            } else {
                throw new ParserException("牌型不正确");
            }
        }
        return this.card;
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
        } else if (o instanceof SerialPairsCardType) {
            if (index > ((SerialPairsCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }
        return -1;
    }
}
