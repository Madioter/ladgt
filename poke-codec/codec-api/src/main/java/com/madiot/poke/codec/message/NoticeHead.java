package com.madiot.poke.codec.message;

import com.madiot.common.spring.SpringContextUtils;
import com.madiot.common.utils.bytes.ByteBuffer;
import com.madiot.common.utils.bytes.ByteUtils;
import com.madiot.poke.codec.api.ICommandFactory;
import com.madiot.poke.codec.api.ICommandType;
import com.madiot.poke.codec.api.IDecodeAble;
import com.madiot.poke.codec.api.IEncodeAble;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.api.IResultFactory;
import com.madiot.poke.codec.common.IndexCreator;
import com.madiot.poke.codec.constants.CodecConstants;
import com.madiot.poke.codec.exception.CodecException;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by julian on 2017/8/19.
 * Head construct 'MADIOT'(5 bytes) + timestamp(8 bytes) + index(8 bytes) + userId(4 bytes) + commandType(1 byte) + result(1 byte) + data Length(4 bytes)
 * total 31 bytes
 */
public class NoticeHead implements IEncodeAble, IDecodeAble {

    private static ICommandFactory commandFactory = SpringContextUtils.getBeanByClass(ICommandFactory.class);

    private static IResultFactory resultFactory = SpringContextUtils.getBeanByClass(IResultFactory.class);

    private static final byte[] START_TAG = CodecConstants.START_TAG.getBytes();

    private Date timestamp = new Date();

    private Long index = IndexCreator.getIndex();

    private Integer userId;

    private ICommandType commandType;

    private INoticeResult result;

    private Integer dataLength;

    public static ICommandType getCommand(byte[] data) {
        return commandFactory.getCommandType(data[CodecConstants.COMMAND_TYPE_INDEX]);
    }

    public static INoticeResult getResult(byte[] data) {
        return resultFactory.getResult(data[CodecConstants.RESULT_TYPE_INDEX]);
    }

    public static long getId(byte[] data) {
        return ByteUtils.bytesToLong(Arrays.copyOfRange(data, 13, 21));
    }

    public NoticeHead(Integer userId, ICommandType commandType, INoticeResult result) {
        this.userId = userId;
        this.commandType = commandType;
        this.result = result;
    }

    public NoticeHead() {

    }

    public NoticeHead(ByteBuffer buffer) {
        decode(buffer);
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (!ArrayUtils.isEquals(buffer.read(START_TAG.length), START_TAG)) {
            throw new CodecException("message code is not start with 'MADIOT', check the message is Complete");
        }
        this.timestamp = new Date(ByteUtils.bytesToLong(buffer.read(CodecConstants.HEAD_TIMESTAMP)));
        this.index = ByteUtils.bytesToLong(buffer.read(CodecConstants.HEAD_INDEX));
        this.userId = ByteUtils.bytesToInt(buffer.read(CodecConstants.HEAD_USER_ID));
        this.commandType = commandFactory.getCommandType(buffer.readInt());
        this.result = resultFactory.getResult(buffer.readInt());
        this.dataLength = ByteUtils.bytesToInt(buffer.read(CodecConstants.HEAD_DATA));
    }

    public void encode(ByteBuffer buffer, int dataLength) {
        this.dataLength = dataLength;
        encode(buffer);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.write(START_TAG);
        buffer.write(ByteUtils.longToBytes(this.timestamp.getTime(), CodecConstants.HEAD_TIMESTAMP));
        buffer.write(ByteUtils.longToBytes(this.index, CodecConstants.HEAD_INDEX));
        buffer.write(ByteUtils.intToBytes(this.userId, CodecConstants.HEAD_USER_ID));
        buffer.write(this.commandType.getBytes());
        buffer.write(this.result.getBytes());
        buffer.write(ByteUtils.intToBytes(this.dataLength, CodecConstants.HEAD_DATA));
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
