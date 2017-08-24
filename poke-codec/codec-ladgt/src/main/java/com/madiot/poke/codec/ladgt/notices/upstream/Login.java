/**
 * @Title: Login.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.upstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;
import com.madiot.common.utils.http.IPUtil;

/**
 * @ClassName: Login
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class Login implements INoticeData {

    private INoticeResult result;

    private String ip;

    private Integer userId;

    public Login(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.ip = IPUtil.deaddr(ByteUtils.bytesToLong(buffer.read(8)));
            this.userId = ByteUtils.bytesToInt(buffer.read(4));
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            buffer.write(ByteUtils.longToBytes(IPUtil.enaddr(this.ip), 8));
            buffer.write(ByteUtils.intToBytes(this.userId, 4));
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
