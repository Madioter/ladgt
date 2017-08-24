/**
 * @Title: LadgtNoticeResult.java
 * @Package com.madiot.poke.codec.ladgt
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt;

import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.common.utils.bytes.ByteUtils;

/**
 * @ClassName: LadgtNoticeResult
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public enum LadgtNoticeResultEnum implements INoticeResult {
    COMMAND(0, "指令"), SUCCESS(1, "成功"), REFUSE(2, "拒绝"), ERROR(3, "异常");

    private String name;

    private Integer code;

    LadgtNoticeResultEnum(Integer code, String name) {
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

    public static LadgtNoticeResultEnum get(Integer resultType) {
        for (LadgtNoticeResultEnum result : LadgtNoticeResultEnum.values()) {
            if (result.getCode().equals(resultType)) {
                return result;
            }
        }
        return null;
    }
}
