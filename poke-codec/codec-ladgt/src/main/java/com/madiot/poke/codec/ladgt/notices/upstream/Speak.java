/**
 * @Title: Speak.java
 * @Package com.madiot.poke.codec.ladgt.notices.upstream
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.upstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.StringType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: Speak
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 */
public class Speak implements INoticeData {

    private INoticeResult result;

    private StringType message = new StringType();

    public Speak(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.message.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.message.decode(buffer);
        }
    }

    public StringType getMessage() {
        return message;
    }

    public void setMessage(StringType message) {
        this.message = message;
    }
}
