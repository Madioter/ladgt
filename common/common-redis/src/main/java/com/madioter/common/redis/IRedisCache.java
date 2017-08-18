package com.madioter.common.redis;

/**
 * Created by Yi.Wang2 on 2016/12/13.
 */
public interface IRedisCache {

    /**
     * 无时限缓存
     *
     * @param type
     * @param key
     * @param value
     */
    public void putNoTimeInCache(String type, Object key, Object value);

    /**
     * 删除
     * @param key 匹配的key
     * @return 删除成功的条数
     */
    public Long delKey(final String type, final String key);
    /**
     * 得到值的字符串，如果不存在返回null
     *
     * @param type 前缀
     * @param key  键
     */
    public String getString(final String type, final String key);

    /**
     * 写入Cache
     *
     * @param type
     * @param key
     * @param value
     * @param seconds
     */
    public String putInCache(final String type, final Object key, final Object value, final int seconds);

    /**
     * 改变存储计数
     *
     * @param type
     * @param key
     * @param value
     */
    public long incrCounterCache(final String type, final Object key, final long value);

    /**
     * 删除缓存
     *
     * @param type
     * @param key
     */
    public Long deleteCache(final String type, final Object key);

    public String getStrCacheName(String type, Object key);

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists
     * 不做任何动作。 时间复杂度：O(1)
     *
     * @param key   key
     * @param value string value
     * @return 设置成功，返回 1 。设置失败，返回 0 。
     */
    public Long setStringIfNotExists(final String key, final String value);

    /**
     * 添加一个指定的值到缓存中.
     *
     * @param key
     * @param value
     * @return
     */
    public void putInCache(String type, Object key, Object value);
}
