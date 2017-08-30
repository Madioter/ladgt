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
import com.madiot.poke.codec.common.StringType;
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

    private StringType username = new StringType();

    private StringType password = new StringType();

    public Login(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.username.decode(buffer);
            this.password.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
           this.username.encode(buffer);
           this.password.encode(buffer);
        }
    }

    public StringType getUsername() {
        return username;
    }

    public void setUsername(StringType username) {
        this.username = username;
    }

    public StringType getPassword() {
        return password;
    }

    public void setPassword(StringType password) {
        this.password = password;
    }
}
