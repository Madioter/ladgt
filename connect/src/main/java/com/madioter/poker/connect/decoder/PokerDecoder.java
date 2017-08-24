/**
 * @Title: PokerDecoder.java
 * @Package com.igdata.gb.connect.decoder
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/3/17
 * @version
 */
package com.madioter.poker.connect.decoder;

import com.madiot.poke.codec.constants.CodecConstants;
import com.madiot.common.utils.bytes.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yi.Wang2
 * @ClassName: PokerDecoder
 * @Description: Poker消息解析，处理粘包和半包问题
 * @date 2017/3/17
 */
public class PokerDecoder extends ByteToMessageDecoder {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PokerDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            in.markReaderIndex();
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return;
            }

            final byte[] buf = new byte[CodecConstants.HEAD_LENGTH];
            int total = in.readableBytes();
            if (total < CodecConstants.HEAD_LENGTH) {
                return;
            }
            in.getBytes(in.readerIndex(), buf);

            int actualLength = getFrameActualLength(buf);
            in.resetReaderIndex();

            if (actualLength <= 0) {
                // 如果当前信息不匹配，则向后寻找下一个匹配的头信息，index向后推一位，继续处理
                in.readerIndex(in.readerIndex() + 1);
                return;
            }
            if (in.readableBytes() < actualLength) {
                // 长度不足，等待下一次处理
            } else {
                // 获取数据并返回
                out.add(in.readBytes(actualLength));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取实际的消息长度
     *
     * @param startCode 其实字符集
     * @return 消息长度
     */
    private int getFrameActualLength(byte[] startCode) {
        if (Arrays.equals(Arrays.copyOfRange(startCode, 0, CodecConstants.START_TAG.length()), CodecConstants.START_TAG.getBytes())) {
            int dataLength = ByteUtils.bytesToInt(Arrays.copyOfRange(startCode, CodecConstants.DATA_LENGTH_POSITION_START,
                    CodecConstants.DATA_LENGTH_POSITION_END));
            return CodecConstants.HEAD_LENGTH + dataLength + CodecConstants.CHECK_CODE_LENGTH;
        } else {
            logger.error("解析失败:" + ByteUtils.bytesToHexString(startCode));
        }
        return -1;
    }
}

