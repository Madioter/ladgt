/**
 * @Title: NoticeHelper.java
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
import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;
import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: NoticeHelper
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class NoticeHelper implements INoticeData {

    private INoticeResult result;

    private LadgtRoleEnum role;

    private LadgtCard pokeCard;

    public NoticeHelper(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.pokeCard.decode(buffer);
            this.role = LadgtRoleEnum.get(buffer.readInt());
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            this.pokeCard.encode(buffer);
            buffer.writeInt(this.role.getCode());
        }
    }

    public LadgtRoleEnum getRole() {
        return role;
    }

    public void setRole(LadgtRoleEnum role) {
        this.role = role;
    }

    public LadgtCard getPokeCard() {
        return pokeCard;
    }

    public void setPokeCard(LadgtCard pokeCard) {
        this.pokeCard = pokeCard;
    }
}
