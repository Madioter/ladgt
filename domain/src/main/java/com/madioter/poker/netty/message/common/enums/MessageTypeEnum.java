package com.madioter.poker.netty.message.common.enums;

/**
 * Created by DELL on 2017/5/10.
 */
public enum MessageTypeEnum implements IEnum {

    LOGIN(0, "登录"), JOIN(1, "加入"), PREPARE(2, "准备"), CALL_LANDLORD(3, "叫地主"),
    CALL_HELPER(4, "叫暗地主"), PLAY(5, "出牌"), HEART(6, "心跳"), LOGOUT(7, "登出"),
    SPEAK(8, "说话");

    private Integer code;

    private String name;

    MessageTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    public static MessageTypeEnum get(int code) {
        for (MessageTypeEnum item : MessageTypeEnum.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
