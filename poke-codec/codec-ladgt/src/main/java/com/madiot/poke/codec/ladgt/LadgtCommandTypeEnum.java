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
import com.madiot.common.utils.bytes.ByteUtils;

/**
 * @ClassName: NoticeDataTypeException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public enum LadgtCommandTypeEnum implements ICommandType, ICommandFactory {

    /**
     * 下行消息
     */
    DEAL(11, "发牌"), CALL_HELPER(12, "叫帮手"), NOTICE_HELPER(13, "通知帮手"), DISCARD(14, "出牌"),
    NOTICE_DISCARD(15, "通知出牌"), NOTICE_SCORE(16, "结算"), NOTICE_SPEAK(17, "发送发言"),

    /**
     * 上行消息
     */
    LOGIN(21, "登录"), READY(22, "准备"), CURRENT_STATE(23, "获取当前状态"), SPEAK(24, "发言");

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
