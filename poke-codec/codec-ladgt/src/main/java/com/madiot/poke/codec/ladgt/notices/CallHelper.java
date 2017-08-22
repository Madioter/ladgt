/**
 * @Title: CallLandLord.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: CallLandLord
 * @Description: TODO
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
