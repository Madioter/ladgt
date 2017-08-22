package com.madiot.poke.codec.message;

import com.madiot.poke.codec.api.ICommandType;
import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeDataFactory;
import com.madiot.poke.codec.api.INoticeMessage;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.exception.CodecException;
import com.madioter.common.spring.SpringContextUtils;
import com.madioter.common.utils.bytes.ByteBuffer;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by julian on 2017/8/19.
 */
public class NoticeMessage implements INoticeMessage {

    private static INoticeDataFactory noticeDataFactory = SpringContextUtils.getBeanByClass(INoticeDataFactory.class);

    private NoticeHead noticeHead;

    private INoticeData noticeData;

    private byte checkCode;

    public NoticeMessage() {
        noticeHead = new NoticeHead();
    }

    public NoticeMessage(Integer userId, ICommandType commandType, INoticeResult result) {
        noticeHead = new NoticeHead(userId, commandType, result);
    }

    public NoticeMessage(byte[] data) {
        noticeHead = new NoticeHead();
        ByteBuffer buffer = new ByteBuffer(data);
        decode(buffer);
    }

    public void decode(byte[] data) {
        ByteBuffer buffer = new ByteBuffer(data);
        decode(buffer);
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (!checkBCC(buffer)) {
            throw new CodecException("bcc check error");
        }
        this.noticeHead.decode(buffer);
        byte[] datas = buffer.read(noticeHead.getDataLength().intValue());
        this.noticeData = noticeDataFactory.getInstance(noticeHead.getCommandType(), noticeHead.getResult());
        this.noticeData.decode(new ByteBuffer(datas));
        this.checkCode = buffer.read();
    }

    private boolean checkBCC(ByteBuffer buffer) {
        byte[] bytes = buffer.getBytes();
        byte bccCode = getBCC(Arrays.copyOfRange(bytes, 0, bytes.length - 1));
        if (bytes[bytes.length - 1] == bccCode) {
            return true;
        }
        return false;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        this.noticeHead.encode(buffer);
        this.noticeData.encode(buffer);
        this.checkCode = getBCC(buffer.getBytes());
        buffer.write(this.checkCode);
    }

    @Override
    public Long getId() {
        return getNoticeHead().getIndex();
    }

    @Override
    public Date getTimestamp() {
        return getNoticeHead().getTimestamp();
    }

    @Override
    public ICommandType getType() {
        return getNoticeHead().getCommandType();
    }

    @Override
    public INoticeData getData() {
        return noticeData;
    }

    public static byte getBCC(byte[] data) {
        byte BCC = 0x00;
        for (int i = 0; i < data.length; i++) {
            BCC = (byte) (BCC ^ data[i]);
        }
        return BCC;
    }

    public void setNoticeData(INoticeData noticeData) {
        this.noticeData = noticeData;
    }

    public NoticeHead getNoticeHead() {
        return noticeHead;
    }
}
