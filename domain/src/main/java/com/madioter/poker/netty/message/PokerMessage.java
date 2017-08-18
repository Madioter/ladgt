package com.madioter.poker.netty.message;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.common.exception.ParserException;
import com.madioter.poker.netty.message.common.constants.MessageConstants;
import com.madioter.poker.netty.message.common.enums.MessageSequenceEnum;
import com.madioter.poker.netty.message.common.enums.MessageTypeEnum;
import com.madioter.poker.netty.message.common.enums.ResultCodeEnum;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.data.impl.MessageDataWrapper;
import com.madioter.poker.netty.message.type.impl.DWordType;
import com.madioter.poker.netty.message.type.impl.DateTime;
import com.madioter.poker.netty.message.type.impl.EnumType;
import com.madioter.poker.netty.message.type.impl.LongType;
import com.madioter.poker.netty.message.type.impl.VersionType;
import com.madioter.poker.netty.message.type.impl.WordType;

import java.util.Arrays;

/**
 * Created by DELL on 2017/5/10.
 * ## (2byte) + version(4byte) + messageType(1byte) + messageTime(8byte) + messageId(8byte) + messageSequence(1byte) + length(4byte)
 */
public class PokerMessage {

    /**
     * 消息头
     */
    private final WordType startTag = new WordType(MessageConstants.PACKAGE_HEADER);

    /**
     * 版本号
     */
    private VersionType version = new VersionType();

    /**
     * 数据类型
     */
    private EnumType<MessageTypeEnum> messageType = EnumType.newInstance(MessageTypeEnum.class);

    /**
     * 数据收到时间
     */
    private DateTime messageTime = new DateTime();

    /**
     * 消息ID
     */
    private LongType messageId = new LongType();

    /**
     * 消息顺序
     */
    private EnumType<MessageSequenceEnum> messageSequence = EnumType.newInstance(MessageSequenceEnum.class);

    /**
     * 结果编码
     */
    private EnumType<ResultCodeEnum> resultCode = EnumType.newInstance(ResultCodeEnum.class);

    /**
     * 数据长度
     */
    private DWordType length = new DWordType();

    /**
     * 消息体
     */
    private IMessageData messageData;

    /**
     * BCC校验位
     */
    private byte checkCode;

    /**
     * 解码
     * @param command 消息体
     * @return
     */
    public static PokerMessage decode(byte[] command) {
        PokerMessage pokerMessage = new PokerMessage();
        ByteBuffer byteBuffer = new ByteBuffer(command);
        if (!Arrays.equals(byteBuffer.read(2), MessageConstants.PACKAGE_HEADER)) {
            throw new ParserException("数据包格式不正确");
        }
        pokerMessage.version.decode(byteBuffer);
        pokerMessage.messageType.decode(byteBuffer);
        pokerMessage.messageTime.decode(byteBuffer);
        pokerMessage.messageId.decode(byteBuffer);
        pokerMessage.messageSequence.decode(byteBuffer);
        pokerMessage.resultCode.decode(byteBuffer);
        pokerMessage.length.decode(byteBuffer);
        if (pokerMessage.messageData == null) {
            pokerMessage.messageData = new MessageDataWrapper(pokerMessage.length.getInt(), pokerMessage.messageType);
            pokerMessage.messageData.decode(byteBuffer);
        } else {
            pokerMessage.messageData.decode(byteBuffer);
        }
        pokerMessage.checkCode = byteBuffer.read();

        byte bcc = getBCC(byteBuffer.reread(0, -1));
        if (bcc != pokerMessage.checkCode) {
            throw new ParserException("校验位验证异常");
        }
        return pokerMessage;
    }

    /**
     * 编码
     * @return 消息体
     */
    public byte[] encode() {
        ByteBuffer byteBuffer = new ByteBuffer();
        startTag.encode(byteBuffer);
        version.encode(byteBuffer);
        messageType.encode(byteBuffer);
        messageTime.encode(byteBuffer);
        messageId.encode(byteBuffer);
        messageSequence.encode(byteBuffer);
        resultCode.encode(byteBuffer);
        length.encode(byteBuffer);
        if (messageData != null) {
            messageData.encode(byteBuffer);
        }
        byte bcc = getBCC(byteBuffer.getBytes());
        byteBuffer.write(bcc);
        return byteBuffer.getBytes();
    }

    public WordType getStartTag() {
        return startTag;
    }

    public VersionType getVersion() {
        return version;
    }

    public void setVersion(VersionType version) {
        this.version = version;
    }

    public EnumType<MessageTypeEnum> getMessageType() {
        return messageType;
    }

    public void setMessageType(EnumType<MessageTypeEnum> messageType) {
        this.messageType = messageType;
    }

    public DateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(DateTime messageTime) {
        this.messageTime = messageTime;
    }

    public DWordType getLength() {
        return length;
    }

    public void setLength(DWordType length) {
        this.length = length;
    }

    public IMessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(IMessageData messageData) {
        this.messageData = messageData;
    }

    public byte getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(byte checkCode) {
        this.checkCode = checkCode;
    }

    public LongType getMessageId() {
        return messageId;
    }

    public void setMessageId(LongType messageId) {
        this.messageId = messageId;
    }

    public EnumType<MessageSequenceEnum> getMessageSequence() {
        return messageSequence;
    }

    public void setMessageSequence(EnumType<MessageSequenceEnum> messageSequence) {
        this.messageSequence = messageSequence;
    }

    public EnumType<ResultCodeEnum> getResultCode() {
        return resultCode;
    }

    public void setResultCode(EnumType<ResultCodeEnum> resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 获取BBC校验码
     *
     * @param data 源数据
     * @return bbc校验结果
     */
    public static byte getBCC(byte[] data) {
        byte bcc = 0x00;
        for (int i = 0; i < data.length; i++) {
            bcc = (byte) (bcc ^ data[i]);
        }
        return bcc;
    }
}
