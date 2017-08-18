package com.madioter.poker.common;

import com.madioter.poker.common.cardtype.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by julian on 2017/5/16.
 */
public abstract class CardType implements Comparable<CardType> {

    protected List<PokerCard> cards;

    public CardType(List<PokerCard> cards) {
        this.cards = cards;

    }

    public static CardType get(List<PokerCard> cards) {
        if (cards.size() == 1) {
            return new SimpleCardType(cards);
        } else if (cards.size() == 2) {
            return new PairsCardType(cards);
        }else {
            if (cards.get(0).getType() == PokerTypeEnum.JOKER) {
                return new JokerBombCardType(cards);
            } else if (cards.size() == 3) {
                return new ThreeCardType(cards);
            } else if (cards.size() == 4) {
                return new BombCardType(cards);
            } else {
                Map<PokerValueEnum, PokerData> map = arrange(cards);
                int size = map.size();
                if (size == 1) {
                    return new BombCardType(cards);
                } else if (size == 2 && cards.size() == 5) {
                    return new ThreeTwoCardType(cards);
                } else {
                    if (Integer.valueOf(cards.size()).doubleValue() / size == 3) {
                        return SerialThreeCardType.get(cards);
                    } else if (Integer.valueOf(cards.size()).doubleValue() / size == 2) {
                        return new SerialPairsCardType(cards);
                    } else {
                        return new ButterflyCardType(cards);
                    }
                }
            }
        }
    }

    public static Map<PokerValueEnum, PokerData> arrange(List<PokerCard> cards) {
        Map<PokerValueEnum, PokerData> map = new HashMap<PokerValueEnum, PokerData>();
        for (PokerCard item : cards) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), new PokerData(item));
            } else {
                map.get(item.getValue()).add();
            }
        }
        return map;
    }

    protected static class PokerData {
        public PokerData(PokerCard card) {
            this.card = card;
        }

        AtomicInteger count = new AtomicInteger(1);

        PokerCard card;

        void add() {
            this.count.incrementAndGet();
        }

        public int getCount() {
            return count.get();
        }

        public PokerCard getCard() {
            return card;
        }
    }

    public static int getIndex(PokerValueEnum cardValue) {
        int index = 0;
        if (cardValue.getCode() <= 2) {
            index = cardValue.getCode() + 13;
        } else if (cardValue.getCode() > 13) {
            index = cardValue.getCode() + 13;
        } else {
            index = cardValue.getCode();
        }
        return index;
    }

    public abstract int getIndex();

}
