/**
 * @Title: MessageSequence.java
 * @Package com.madioter.poker.netty.message.common.enums
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/5/11
 * @version
 */
package com.madioter.poker.netty.message.common.enums;

/**
 * @ClassName: MessageSequence
 * @Description: 消息顺序
 * @author Yi.Wang2
 * @date 2017/5/11
 */
public enum MessageSequenceEnum implements IEnum {

    REQUEST(0, "请求"), RECEIVED(1, "接受结果"), RESULT(2, "执行结果"), NOTIFY(4, "通知"), FINISH(5, "结束交互");

    private Integer code;

    private String name;

    MessageSequenceEnum(int code, String name) {
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

    public static MessageSequenceEnum get(int code) {
        for (MessageSequenceEnum item : MessageSequenceEnum.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
