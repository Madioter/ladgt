package com.madioter.poker.netty.message.common.enums;

/**
 * Created by DELL on 2017/5/7.
 */
public enum RoleEnum implements IEnum {

    LANDLORDER(1, "地主"), FARMER(2, "农民"), HELPER(3, "暗地主"), JOINER(4, "参与者"), BYSTANDER(5, "观看者"), LANDLORDER_ALONE(6,"单地主");

    private Integer code;

    private String name;

    RoleEnum(Integer code, String name) {
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
