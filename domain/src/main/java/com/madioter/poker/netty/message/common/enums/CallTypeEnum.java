package com.madioter.poker.netty.message.common.enums;

/**
 * Created by julian on 2017/5/16.
 */
public enum CallTypeEnum implements IEnum {

    CALL(1,"叫"), PASS(2,"过");

    private Integer code;

    private String name;

    CallTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
