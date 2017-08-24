/**
 * @Title: NoticeMessageFuture.java
 * @Package com.madiot.poke.context.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.context.model;

import com.madiot.poke.codec.message.NoticeMessage;
import com.madioter.poker.common.future.CallbackFuture;

/**
 * @ClassName: NoticeMessageFuture
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class NoticeMessageFuture extends CallbackFuture<NoticeMessage> {

    private Long index;

    public NoticeMessageFuture(Long index) {
        this.index = index;
    }

    @Override
    public boolean completed(NoticeMessage result) {
        if (result.getId().equals(index)) {
            return super.completed(result);
        } else {
            return false;
        }
    }

    public Long getIndex() {
        return index;
    }
}
