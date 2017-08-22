/**
 * @Title: NoticeDataTypeException.java
 * @Package com.madiot.poke.codec.ladgt
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt;

import com.madiot.poke.codec.api.ICommandFactory;
import com.madiot.poke.codec.api.ICommandType;
import com.madioter.common.utils.bytes.ByteUtils;

/**
 * @ClassName: NoticeDataTypeException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public enum LadgtCommandTypeEnum implements ICommandType, ICommandFactory {

    LOGIN(1, "登录"), READY(2, "准备"), DEAL(3, "发牌"), CALL_HELPER(4, "叫帮手"),
    NOTICE_HELPER(5, "通知帮手"), DISCARD(6, "出牌"), PASS(7, "过牌"),
    NOTICE_DISCARD(8, "通知出牌"), NOTICE_SCORE(9, "结算");

    private String name;

    private Integer code;

    LadgtCommandTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public byte[] getBytes() {
        return ByteUtils.intToBytes(this.code, 1);
    }

    @Override
    public ICommandType getCommandType(int commandType) {
        return get(commandType);
    }

    public static LadgtCommandTypeEnum get(Integer commandType) {
        for (LadgtCommandTypeEnum result : LadgtCommandTypeEnum.values()) {
            if (result.getCode().equals(commandType)) {
                return result;
            }
        }
        return null;
    }
}
