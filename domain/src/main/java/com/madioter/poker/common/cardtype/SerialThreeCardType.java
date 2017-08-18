package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerTypeEnum;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.exception.ParserException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/5/20.
 */
public class SerialThreeCardType extends CardType {

    private int index;

    private PokerCard card;

    public static CardType get(List<PokerCard> cards) {
        if (cards.get(0).getType() == PokerTypeEnum.JOKER) {
            return new BombCardType(cards);
        } else {
            return new SerialThreeCardType(cards);
        }
    }

    public SerialThreeCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
        this.index = getIndex(this.card.getValue());
    }

    private PokerCard getCard(List<PokerCard> cards) {
        Map<PokerValueEnum, PokerData> map = arrange(cards);
        List<Integer> pokerCardIndex = new ArrayList<>();
        for (Map.Entry<PokerValueEnum, CardType.PokerData> entry : map.entrySet()) {
            if (entry.getValue().getCount() > 1) {
                pokerCardIndex.add(getIndex(entry.getKey()));
                if (this.card == null || getIndex(this.card.getValue()) < getIndex(entry.getKey())) {
                    this.card = entry.getValue().getCard();
                }
            }
        }
        Collections.sort(pokerCardIndex);
        Integer current = null;
        for (Integer index : pokerCardIndex) {
            if (index == getIndex(PokerValueEnum.V_2)) {
                throw new ParserException("牌型不正确");
            }
            if (current == null || current + 1 == index) {
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
        } else if (o instanceof SerialThreeCardType) {
            if (index > ((SerialThreeCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }
        return -1;
    }
}
