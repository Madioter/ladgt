package com.madioter.poker.common;

/**
 * Created by DELL on 2017/5/7.
 */
public class PokerDO {

    public static final Integer MAX_INDEX = 54;

    public static final Integer RED_JOKER_INDEX = 53;

    public static final Integer BLACK_JOKER_INDEX = 52;

    private static PokerCard[] pokerCards = new PokerCard[MAX_INDEX];

    static {
        int index = 0;
        for (PokerValueEnum pokerValue : PokerValueEnum.values()) {
            if (pokerValue.getCode() <= PokerValueEnum.V_K.getCode()) {
                for (PokerTypeEnum pokerType : PokerTypeEnum.values()) {
                    if (pokerType != PokerTypeEnum.JOKER) {
                        pokerCards[index] = new PokerCard(pokerType, pokerValue);
                        index++;
                    }
                }
            }
        }
        pokerCards[BLACK_JOKER_INDEX] = new PokerCard(PokerTypeEnum.JOKER, PokerValueEnum.BLACK_JOKER);
        pokerCards[RED_JOKER_INDEX] = new PokerCard(PokerTypeEnum.JOKER, PokerValueEnum.RED_JOKER);
    }

    public static int getIndex(PokerCard card) {
        for (int i = 0; i < pokerCards.length; i++) {
            if (pokerCards[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndex(PokerTypeEnum pokerType, PokerValueEnum pokerValue) {
        PokerCard card = new PokerCard(pokerType, pokerValue);
        return getIndex(card);
    }
}
