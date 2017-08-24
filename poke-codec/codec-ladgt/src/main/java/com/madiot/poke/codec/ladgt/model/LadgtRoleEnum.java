/**
 * @Title: LadgtRoleEnum.java
 * @Package com.madiot.poke.codec.ladgt.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.model;

import com.madiot.poke.codec.common.IRoleEnum;

/**
 * @ClassName: LadgtRoleEnum
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public enum LadgtRoleEnum implements IRoleEnum {
    LAND_LORD(1), FARMER(2), HELPER(3), LAND_LORD_WITH_HELPER(4);


    private Integer code;

    LadgtRoleEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static LadgtRoleEnum get(Integer code) {
        for (LadgtRoleEnum item : LadgtRoleEnum.values()) {
            if (item.code.equals(code)) {
                return item;
            }
        }
        return null;
    }
}
