/**
 * @Title: CallLandLord.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.downstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: CallHelper
 * @Description: 服务器发送叫帮手指令，地主客户端在符合条件的情况下执行，其他情况等待地主响应完成，超时时间15秒
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class CallHelper implements INoticeData {

    private INoticeResult result;

    private LadgtCard pokeCard;

    public CallHelper(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.pokeCard.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.pokeCard.encode(buffer);
        }
    }

    public LadgtCard getPokeCard() {
        return pokeCard;
    }

    public void setPokeCard(LadgtCard pokeCard) {
        this.pokeCard = pokeCard;
    }
}
