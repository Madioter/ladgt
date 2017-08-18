package com.madioter.poker.common;

import com.madioter.poker.netty.message.common.enums.IEnum;

/**
 * Created by DELL on 2017/5/7.
 */
public enum PokerValueEnum implements IEnum {

    V_A(1, "A"), V_2(2, "2"), V_3(3, "3"), V_4(4, "4"), V_5(5, "5"), V_6(6, "6"),
    V_7(7, "7"), V_8(8, "8"), V_9(9, "9"), V_10(10, "10"), V_J(11, "J"), V_Q(12, "Q"),
    V_K(13, "K"), BLACK_JOKER(14, "black joker"), RED_JOKER(15, "red joker");

    private String name;

    private Integer code;

    PokerValueEnum(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public PokerValueEnum get(int code) {
        for(PokerValueEnum item: PokerValueEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
