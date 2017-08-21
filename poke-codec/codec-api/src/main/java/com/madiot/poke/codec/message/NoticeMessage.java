package com.madiot.poke.codec.message;

import com.madiot.poke.codec.api.*;
import com.madiot.poke.codec.exception.CodecException;
import com.madioter.common.spring.SpringContextUtils;
import com.madioter.common.utils.bytes.ByteBuffer;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by julian on 2017/8/19.
 */
public class NoticeMessage implements INoticeMessage {

    private static INoticeDataFactory noticeDataFactory = SpringContextUtils.getBeanByClass(INoticeDataFactory.class);

    private NoticeHead noticeHead = new NoticeHead();

    private INoticeData noticeData;

    private byte checkCode;

    public NoticeMessage() {

    }

    public NoticeMessage(byte[] data) {
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
        return null;
    }

    @Override
    public Date getTimestamp() {
        return null;
    }

    @Override
    public INoticeType getType() {
        return null;
    }

    @Override
    public INoticeData getData() {
        return null;
    }


    public static byte getBCC(byte[] data) {
        byte BCC = 0x00;
        for (int i = 0; i < data.length; i++) {
            BCC = (byte) (BCC ^ data[i]);
        }
        return BCC;
    }
}
