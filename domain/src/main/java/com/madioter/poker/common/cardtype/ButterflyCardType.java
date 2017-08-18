package com.madioter.poker.common.cardtype;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.exception.ParserException;

import java.util.*;

/**
 * Created by julian on 2017/5/20.
 */
public class ButterflyCardType extends CardType {

    private int index;

    private PokerCard card;

    public ButterflyCardType(List<PokerCard> cards) {
        super(cards);
        this.card = getCard(cards);
        this.index = getIndex(this.card.getValue());
    }

    private PokerCard getCard(List<PokerCard> cards) {
        Map<PokerValueEnum, PokerData> map = arrange(cards);
        List<Integer> threeIndex = new ArrayList<>();
        List<Integer> twoIndex = new ArrayList<>();
        for (Map.Entry<PokerValueEnum, CardType.PokerData> entry : map.entrySet()) {
            if (entry.getValue().getCount() == 2) {
                twoIndex.add(getIndex(entry.getKey()));
            } else if (entry.getValue().getCount() == 3) {
                threeIndex.add(getIndex(entry.getKey()));
                if (this.card == null || getIndex(this.card.getValue()) < getIndex(entry.getKey())) {
                    this.card = entry.getValue().getCard();
                }
            } else if (entry.getValue().getCount() == 5) {
                twoIndex.add(getIndex(entry.getKey()));
                threeIndex.add(getIndex(entry.getKey()));
                if (this.card == null || getIndex(this.card.getValue()) < getIndex(entry.getKey())) {
                    this.card = entry.getValue().getCard();
                }
            }
        }
        Collections.sort(threeIndex);
        Integer current = null;
        for (Integer index : threeIndex) {
            if (index == getIndex(PokerValueEnum.V_2)) {
                throw new ParserException("牌型不正确");
            }
            if (current == null || (current + 1) == index) {
                current = index;
            } else {
                throw new ParserException("牌型不正确");
            }
        }
        Collections.sort(twoIndex);
        current = null;
        Iterator<Integer> iterator = twoIndex.iterator();
        while (iterator.hasNext()) {
            Integer index = iterator.next();
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
        } else if (o instanceof ButterflyCardType) {
            if (index > ((ButterflyCardType) o).index) {
                return 1;
            }
        } else {
            throw new ParserException("牌型不正确");
        }

        return -1;
    }
}
