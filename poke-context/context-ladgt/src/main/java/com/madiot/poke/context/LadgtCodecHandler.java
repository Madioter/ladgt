/**
 * @Title: LadgtCodecHandler.java
 * @Package com.madiot.poke.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.context;

import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtCommandTypeEnum;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.notices.CallHelper;
import com.madiot.poke.codec.ladgt.notices.Deal;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.observer.LadgtPlayObserver;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: LadgtCodecHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtCodecHandler {

    public static byte[] buildDealMessage(LadgtPlayObserver observer) {
        Deal deal = (Deal) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.DEAL, LadgtNoticeResultEnum.COMMAND);
        ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);
        for (LadgtPokeCard pokeCard : observer.getPokeCards()) {
            cards.add(new LadgtCard(pokeCard.getIndex()));
        }
        deal.setCards(cards);
        deal.setRole(observer.getRole());
        deal.setLandLordCard(new LadgtCard(observer.getLandlordCard().getIndex()));
        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.DEAL, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(deal);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }

    public static byte[] buildCallHelperMessage(LadgtPlayObserver observer) {
        CallHelper callHelper = (CallHelper) observer.getConfiguraction().getNoticeDataFactory().getInstance(LadgtCommandTypeEnum.CALL_HELPER, LadgtNoticeResultEnum.COMMAND);
        NoticeMessage message = new NoticeMessage(observer.getPlayer().getId(), LadgtCommandTypeEnum.CALL_HELPER, LadgtNoticeResultEnum.COMMAND);
        message.setNoticeData(callHelper);
        ByteBuffer byteBuffer = new ByteBuffer();
        message.encode(byteBuffer);
        return byteBuffer.getBytes();
    }
}
