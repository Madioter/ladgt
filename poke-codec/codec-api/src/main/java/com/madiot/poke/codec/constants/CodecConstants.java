/**
 * @Title: CodecConstants.java
 * @Package com.madiot.poke.codec.constants
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.codec.constants;

/**
 * @ClassName: CodecConstants
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public interface CodecConstants {
    /**
     * TAG
     */
    public static final String START_TAG = "MADIOT";

    /**
     * MESSAGE HEAD TOTAL LENGTH
     */
    public static final int HEAD_LENGTH = 31;

    /**
     * MESSAGE BUILD TIMESTAMP
     */
    public static final int HEAD_TIMESTAMP = 8;

    /**
     * MESSAGE INDEX DATA LENGTH
     */
    public static final int HEAD_INDEX = 8;

    /**
     * MESSAGE USER ID DATA LENGTH
     */
    public static final int HEAD_USER_ID = 4;

    /**
     * MESSAGE COMMAND TYPE DATA LENGTH
     */
    public static final int HEAD_COMMAND_TYPE = 1;

    /**
     * MESSAGE RESULT TYPE DATA LENGTH
     */
    public static final int HEAD_RESULT_TYPE = 1;

    /**
     * BODY DATA LENGTH
     */
    public static final int HEAD_DATA = 4;

    public static final int DATA_LENGTH_POSITION_START = 27;

    public static final int DATA_LENGTH_POSITION_END = 31;

    public static final int CHECK_CODE_LENGTH = 1;

    public static final int COMMAND_TYPE_INDEX = 25;

    public static final int RESULT_TYPE_INDEX = 26;
}
