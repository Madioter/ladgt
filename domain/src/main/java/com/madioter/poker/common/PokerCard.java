package com.madioter.poker.common;

/**
 * Created by DELL on 2017/5/7.
 */
public class PokerCard {

    public PokerCard(PokerTypeEnum type, PokerValueEnum value) {
        this.type = type;
        this.value = value;
    }

    private PokerTypeEnum type;

    private PokerValueEnum value;

    public PokerTypeEnum getType() {
        return type;
    }

    public void setType(PokerTypeEnum type) {
        this.type = type;
    }

    public PokerValueEnum getValue() {
        return value;
    }

    public void setValue(PokerValueEnum value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PokerCard) {
            if (((PokerCard) obj).getType() == type && ((PokerCard) obj).getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
