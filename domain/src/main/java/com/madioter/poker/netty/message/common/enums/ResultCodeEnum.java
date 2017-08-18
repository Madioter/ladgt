package com.madioter.poker.netty.message.common.enums;

/**
 * Created by julian on 2017/5/16.
 */
public enum ResultCodeEnum implements IEnum {
    SUCCESS(1,"成功"), FAILURE(2,"失败");

    private Integer code;

    private String name;

    ResultCodeEnum(Integer code,String name) {
        this.code = code;
        this.name = name;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
