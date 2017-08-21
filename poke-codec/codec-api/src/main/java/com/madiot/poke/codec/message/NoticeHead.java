package com.madiot.poke.codec.message;

import com.madiot.poke.codec.api.*;
import com.madiot.poke.codec.exception.CodecException;
import com.madioter.common.spring.SpringContextUtils;
import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.Date;

/**
 * Created by julian on 2017/8/19.
 * Head construct 'MADIOT'(5 bytes) + timestamp(8 bytes) + index(8 bytes) + commandType(1 byte) + result(1 byte) + data Length(4 bytes)
 * total 27 bytes
 */
public class NoticeHead implements IEncodeAble, IDecodeAble {

    private static ICommandFactory commandFactory = SpringContextUtils.getBeanByClass(ICommandFactory.class);

    private static IResultFactory resultFactory = SpringContextUtils.getBeanByClass(IResultFactory.class);

    private static final byte[] START_TAG = "MADIOT".getBytes();

    private Date timestamp;

    private Long index;

    private ICommandType commandType;

    private INoticeResult result;

    private Integer dataLength;

    @Override
    public void decode(ByteBuffer buffer) {
        if (!ArrayUtils.isEquals(buffer.read(5), START_TAG)) {
            throw new CodecException("message code is not start with 'MADIOT', check the message is Complete");
        }
        this.timestamp = new Date(ByteUtils.bytesToLong(buffer.read(8)));
        this.index = ByteUtils.bytesToLong(buffer.read(8));
        this.commandType = commandFactory.getCommandType(buffer.readInt());
        this.result = resultFactory.getResult(buffer.readInt());
        this.dataLength = ByteUtils.bytesToInt(buffer.read(4));
    }

    public void encode(ByteBuffer buffer, int dataLength) {
        this.dataLength = dataLength;
        encode(buffer);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.write(START_TAG);
        buffer.write(ByteUtils.longToBytes(this.timestamp.getTime(),8));
        buffer.write(ByteUtils.longToBytes(this.index,8));
        buffer.write(this.commandType.getBytes());
        buffer.write(this.result.getBytes());
        buffer.write(ByteUtils.intToBytes(this.dataLength,4));
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public ICommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(ICommandType commandType) {
        this.commandType = commandType;
    }

    public INoticeResult getResult() {
        return result;
    }

    public void setResult(INoticeResult result) {
        this.result = result;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }
}
