/**
 * @Title: RedisKeyConstant.java
 * @Package com.madiot.common.redis.constants
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.constants;

/**
 * @ClassName: RedisKeyConstant
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class RedisKeyConstant {

    public static final String PLAYER_CONNECT_INFO_KEY = "player_connect_info";

    public static final String ROUND_INFO_KEY = "round_info";

    /**
     * 连接信息的最长有效期
     */
    public static final int PLAYER_CONNECT_INFO_SECOND = 7 * 24 * 60 * 60;

    /**
     * 连接信息的最长有效期
     */
    public static final int ROUND_INFO_SECOND = 10 * 60;
}
