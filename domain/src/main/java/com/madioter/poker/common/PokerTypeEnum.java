package com.madioter.poker.common;

import com.madioter.poker.netty.message.common.enums.IEnum;

/**
 * Created by DELL on 2017/5/7.
 */
public enum PokerTypeEnum implements IEnum {

    HEARTS(1, "红桃"), SPADES(2, "黑桃"), DIAMODS(3, "方块"), CLUBS(4, "梅花"), JOKER(5, "王");

    private String name;

    private Integer code;

    PokerTypeEnum(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public PokerTypeEnum get(int code) {
        for (PokerTypeEnum item : PokerTypeEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
