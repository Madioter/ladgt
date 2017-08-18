package com.madioter.poker.netty.message.common.constants;

/**
 * Created by DELL on 2017/5/10.
 */
public class MessageConstants {

    /**
     * 异常状态值
     */
    public static final int ERROR_STATE = 0xFE;

    /**
     * 无效状态值
     */
    public static final int INVALID_STATE = 0xFF;

    /**
     * 无符号单字节整型（字节，8位）。
     */
    public static final int BYTE_SIZE = 1;

    /**
     * 无符号双字节整型（字，16位）。
     */
    public static final int WORD_SIZE = 2;

    /**
     * 无符号四字节整型（双字，32位）。
     */
    public static final int DWORD_SIZE = 4;

    /**
     * 长整型的数据占用8个字节（8字节，64位）
     */
    public static final int LONG_SIZE = 8;

    /**
     * 标准字符集为：UTF-8
     */
    public static final String DEFAULT_CHARACTER_SET = "UTF-8";

    /**
     * 包头标识：## 0x23,0x23
     */
    public static final byte[] PACKAGE_HEADER = new byte[]{0x23, 0x23};

    /**
     * 包头总长度 28字节
     */
    public static final int PACKAGE_HEADER_LENGTH = 28;

    /**
     * 包头中申明数据项长度的数据byte起始位置
     */
    public static final int DATA_LENGTH_POSITION_START = 24;

    /**
     * 包头中申明数据项长度的数据byte结束位置
     */
    public static final int DATA_LENGTH_POSITION_END = 28;

    /**
     * 包中验证位的长度
     */
    public static final int CHECK_CODE_LENGTH = 1;

    /**
     * get方法头
     */
    public static final String GET_METHOD_TAG = "get";

    /**
     * set方法头
     */
    public static final String SET_METHOD_TAG = "set";
}
