package com.madioter.common.redis;

import com.madioter.common.spring.SpringContextUtils;

/**
 * Created by DELL on 2017/5/7.
 */
public class IncreaseIdCreator {

    private static IRedisCache redisCache = SpringContextUtils.getBeanByClass(IRedisCache.class);

    public static long getNextId(String key) {
        return redisCache.incrCounterCache("incr_id", key, 1);
    }
}
