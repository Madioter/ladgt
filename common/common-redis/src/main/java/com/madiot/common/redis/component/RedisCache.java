package com.madiot.common.redis.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * changed for redis cluster
 * @author jenkin
 *
 */
public class RedisCache implements IRedisCache{
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    @Autowired JedisCluster jedis;
    // private static String PREFIX =
    // ConfigUtil.getString("redis.config.prefix");
    private static String PREFIX = "";
    /**
     * 缓存180秒
     */
    public static final int REF_SECONDS = 180;
    /**
     * 它保证在执行操作之后释放数据源returnResource(jedis)
     *
     */

    /**
     * 删除模糊匹配的key
     *
     * @param likeKey 模糊匹配的key
     * @return 删除成功的条数
     */
    public long delKeysLike(final String likeKey) {
        final String preKey = PREFIX + likeKey;
        logger.debug("\n delKeysLike Redis 前缀 ：" + PREFIX + "\n");
        try {
            return new Executor<Long>() {
                @Override
                Long execute() {
                    long count = 0;
                    final Map<String, JedisPool> clusterNodes = jedis.getClusterNodes();
                    for (String k : clusterNodes.keySet()) {
                        JedisPool jp = clusterNodes.get(k);
                        Jedis _jedis = jp.getResource();
                        try {
                            Set<String> keys = _jedis.keys(preKey + "*");
                            for (String s : keys) {
                                _jedis.del(s);
                                count++;
                            }
                        } catch (Exception e) {
                            logger.error("Getting keys error: {}", e);
                        } finally {
                            _jedis.close();
                        }
                    }
                    return count;
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }

    }

    /**
     * 模糊查询key
     *
     * @param likeKey
     * @return
     */
    public Set<String> queryKeysLike(final String likeKey) {
        final String preKey = PREFIX + likeKey;
        try {
            return new Executor<Set<String>>() {
                @Override
                Set<String> execute() {
                    final Set<String> keys = new TreeSet<>();
                    final Map<String, JedisPool> clusterNodes = jedis.getClusterNodes();
                    for (String k : clusterNodes.keySet()) {
                        JedisPool jp = clusterNodes.get(k);
                        Jedis _jedis = jp.getResource();
                        try {
                            keys.addAll(_jedis.keys(preKey + "*"));
                        } catch (Exception e) {
                            logger.error("Getting keys error: {}", e);
                        } finally {
                            _jedis.close();
                        }
                    }
                    return keys;
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 删除
     * @param key 匹配的key
     * @return 删除成功的条数
     */
    public Long delKey(final String key) {
        if (key == null || "".equals(key.trim())) {
            return 0l;
        }
        try {
            final String preKey = PREFIX + key;
            logger.debug("\n delKey  Redis 前缀 ：" + PREFIX + "\n");
            return new Executor<Long>() {
                @Override
                Long execute() {
                    return jedis.del(preKey);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return 0l;
        }
    }

    /**
     * 删除
     *
     * @param keys 匹配的key的集合
     * @return 删除成功的条数
     */
    public Long delKeys(final String[] keys) {
        try {
            return new Executor<Long>() {
                @Override
                Long execute() {
                    long count = 0;
                    final Map<String, JedisPool> clusterNodes = jedis.getClusterNodes();
                    for (String k : clusterNodes.keySet()) {
                        JedisPool jp = clusterNodes.get(k);
                        Jedis _jedis = jp.getResource();
                        try {
                            for (String s : keys) {
                                try {
                                    count +=_jedis.del(s);
                                } catch (Exception e) {
                                    continue;
                                }
                            }
                        } catch (Exception e) {
                            logger.error("del keys error: {}", e);
                        } finally {
                            _jedis.close();
                        }
                    }
                    return count;
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 在 Redis 中，带有生存时间的 key
     * 被称为『可挥发』(volatile)的。
     * @param key
     * @param expire 生命周期，单位为秒
     * @return 1: 设置成功 0: 已经超时或key不存在
     */
    public Long expire(final String key, final int expire) {
        try {
            return new Executor<Long>() {
                @Override
                Long execute() {
                    return jedis.expire(key, expire);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 一个跨jvm的id生成器，利用了redis原子性操作的特点
     *
     * @param key
     *            id的key
     * @return 返回生成的Id
     */
    public Long makeId(final String key) {
        try {
            return new Executor<Long>() {
                @Override
                Long execute() {
                    long id = jedis.incr(key);
                    if ((id + 75807) >= Long.MAX_VALUE) {
                        // 避免溢出，重置，getSet命令之前允许incr插队，75807就是预留的插队空间
                        jedis.getSet(key, "0");
                    }
                    return id;
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 将字符串值 value 关联到 key 。 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当 setString 成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     * 时间复杂度：O(1)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @return 在设置操作成功完成时，才返回 OK 。
     */
    public String setString(final String key, final String value) {
        try {
            final String preKey = PREFIX + key;
            logger.debug("\n setString  Redis 前缀 ：" + PREFIX + "\n");
            return new Executor<String>() {
                @Override
                String execute() {
                    return jedis.set(preKey, value);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

	/*
	 * ======================================Strings============================
	 * ==========
	 */

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 expire (以秒为单位)。 如果 key 已经存在， 将覆写旧值。
     * 类似于以下两个命令: SET key value EXPIRE key expire # 设置生存时间
     * 不同之处是这个方法是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，在 Redis 用作缓存时，非常实用。
     * 时间复杂度：O(1)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @param expire
     *            生命周期
     * @return 设置成功时返回 OK 。当 expire 参数不合法时，返回一个错误。
     */
    public String setString(final String key, final String value, final int expire) {
        try {
            final String preKey = PREFIX + key;
            logger.debug("\n setString  Redis 前缀 ：" + PREFIX + "\n");
            return new Executor<String>() {
                @Override
                String execute() {
                    return jedis.setex(preKey, expire, value);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists
     * 不做任何动作。 时间复杂度：O(1)
     *
     * @param key
     *            key
     * @param value
     *            string value
     * @return 设置成功，返回 1 。设置失败，返回 0 。
     */
    public Long setStringIfNotExists(final String key, final String value) {
        try {
            return new Executor<Long>() {
                @Override
                Long execute() {
                    return jedis.setnx(key, value);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。 假如 key 储存的值不是字符串类型，返回一个错误，因为
     * getString 只能用于处理字符串值。 时间复杂度: O(1)
     *
     * @param key
     *            key
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
     */
    public String getString(final String key) {
        try {
            final String preKey = PREFIX + key;
            logger.debug("\n getString  Redis 前缀 ：" + PREFIX + "\n");
            return new Executor<String>() {
                @Override
                String execute() {
                    return jedis.get(preKey);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 批量的 {@link #setString(String, String)}
     *
     * @param pairs
     *            键值对数组{数组第一个元素为key，第二个元素为value}
     * @return 操作状态的集合
     */
    public List<Object> batchSetString(final List<Pair<String, String>> pairs) {
        try {
            return new Executor<List<Object>>() {
                @Override
                List<Object> execute() {
                    JedisClusterPipeline pipeline = JedisClusterPipeline.pipelined(jedis);
                    try {
                        pipeline.refreshCluster();
                        for (Pair<String, String> pair : pairs) {
                            pipeline.set(pair.getKey(), pair.getValue());
                        }
                        return pipeline.syncAndReturnAll();
                    } finally {
                        pipeline.close();
                    }
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 批量的 {@link #getString(String)}
     *
     * @param keys  key数组
     * @return value的集合
     */
    public List<String> batchGetString(final String[] keys) {
        final List<String> _res = new ArrayList<>();
        try {
            return new Executor<List<String>>() {
                @Override
                List<String> execute() {
                    JedisClusterPipeline pipeline = JedisClusterPipeline.pipelined(jedis);
                    try {
                        pipeline.refreshCluster();
                        for (String key : keys) {
                            pipeline.get(key);
                        }
                        List<Object> _obs = pipeline.syncAndReturnAll();
                        if(_obs != null && _obs.size() > 0){
                            for (Object ob : _obs) {
                                _res.add(ob.toString());
                            }
                        }
                        return _res;
                    } finally {
                        pipeline.close();
                    }
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

	/*
	 * ======================================List===============================
	 * =======
	 */

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @return 执行 listPushHead 命令后，列表的长度。
     */
    public Long listPushHead(final String key, final String value) {
        return new Executor<Long>() {
            @Override
            Long execute() {
                return jedis.lpush(key, value);
            }
        }.getResult();
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头, 当列表大于指定长度是就对列表进行修剪(trim)
     *
     * @param key
     * @param value
     * @param size 链表超过这个长度就修剪元素
     * @return 执行 listPushHeadAndTrim 命令后，列表的长度。
     */
    public Long listPushHeadAndTrim(final String key, final String value, final long size) {
        return new Executor<Long>() {
            @Override
            Long execute() {
                long _l = jedis.lpush(key, value);
                // 修剪列表元素, 如果 size - 1 比 end 下标还要大，Redis将 size 的值设置为 end 。
                jedis.ltrim(key, 0, size - 1);
                return _l>size?size:_l;
            }
        }.getResult();
    }

    private byte[] getCacheName(String type, Object key) {
        StringBuilder cacheName = new StringBuilder(type);
        if (key != null && key.toString().length() > 0) {
            cacheName.append("_").append(key);
        }
        return cacheName.toString().getBytes();
    }

    /**
     * 序列化
     *
     * @param value
     * @return
     */
    public static byte[] getSerializable(Object value) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(value);
            return buffer.toByteArray();
        } catch (IOException e) {
            logger.error("ERROR:", e);
        }
        return null;
    }

    public String getStrCacheName(String type, Object key) {
        StringBuilder cacheName = new StringBuilder(type);
        if (key != null && key.toString().length() > 0) {
            cacheName.append("_").append(key);
        }
        return cacheName.toString();
    }

    /**
     * 写入Cache
     *
     * @param type
     * @param key
     * @param value
     * @param seconds
     */
    public String putInCache(final String type, final Object key, final Object value, final int seconds) {
        return new Executor<String>() {
            @Override
            String execute() {
                String result = null;
                try {
                    String cacheName = getStrCacheName(type, key);
                    String v = value.toString();
                    if (v != null) {
                        if (seconds < 1) {
                            jedis.set(cacheName, v);
                        } else {
                            jedis.setex(cacheName, seconds, v);
                        }
                    }
                    return result;
                } catch (Exception e) {
                    logger.error("cache " + getCacheName(type, key) + " socket error");
                    return result;
                }
            }
        }.getResult();
    }

    /**
     * 无时限缓存
     *
     * @param type
     * @param key
     * @param value
     */
    public void putNoTimeInCache(String type, Object key, Object value) {
        if (value != null) {
            putInCache(type, key, value, -1);
        }
    }

    /**
     * 删除对应的type_key
     * @param type
     * @param key 匹配的key
     * @return
     */
    @Override
    public Long delKey(final String type, final String key) {
        if (key == null || "".equals(key.trim())) {
            return 0L;
        }
        try {
            final String preKey = type + "_" + key;
            logger.debug("\n delKey  Redis 前缀 ：" + type + "\n");
            return new Executor<Long>() {
                @Override
                Long execute() {
                    return jedis.del(preKey);
                }
            }.getResult();
        } catch (Exception e) {
            logger.error("", e);
            return 0L;
        }
    }

    /**
     * 添加一个指定的值到缓存中.
     *
     * @param key
     * @param value
     * @return
     */
    public void putInCache(String type, Object key, Object value) {
        putInCache(type, key, value, REF_SECONDS);
    }

    /**
     * 删除缓存
     *
     * @param type
     * @param key
     */
    public Long deleteCache(final String type, final Object key) {
        return new Executor<Long>() {
            @Override
            Long execute() {
                return jedis.del(getCacheName(type, key));
            }
        }.getResult();
    }

    /**
     * 存储计数器
     *
     * @param type
     * @param key
     * @param value
     */
    public long putCounterInCache(final String type, final Object key, final long value) {
        return new Executor<Long>() {
            @Override
            Long execute() {
                try {
                    long ret = jedis.incrBy(getCacheName(type, key), value);
                    return ret;
                } catch (Exception e) {
                    logger.error("cache " + getCacheName(type, key) + " error");
                }
                return 0l;
            }
        }.getResult();
    }

    /**
     * 改变存储计数
     *
     * @param type
     * @param key
     * @param value
     */
    public long incrCounterCache(final String type, final Object key, final long value) {
        return new Executor<Long>() {
            @Override
            Long execute() {
                long ret = 0;
                try {
                    if (value >= 0) {
                        ret = jedis.incrBy(getCacheName(type, key), Math.abs(value));
                    } else {
                        ret = jedis.decrBy(getCacheName(type, key), Math.abs(value));
                    }
                } catch (Exception e) {
                    logger.error("cache " + getCacheName(type, key) + " error", e);
                }
                return ret;
            }
        }.getResult();
    }

    /**
     * 得到值的二进制数组，如果不存在返回null
     *
     * @param type
     *            前缀
     * @param key
     *            键
     */
    public byte[] getObject(final String type, final String key) {
        return new Executor<byte[]>() {
            @Override
            byte[] execute() {
                try {
                    byte[] v = jedis.get(getCacheName(type, key));
                    if (v != null) {
                        return v;
                    }
                } catch (Exception e) {
                    logger.error("cache " + getCacheName(type, key) + " error");
                }
                return null;
            }
        }.getResult();
    }

    /**
     * 得到值的字符串，如果不存在返回null
     *
     * @param type
     *            前缀
     * @param key
     *            键
     */
    public String getString(final String type, final String key) {
        byte[] bytes = getObject(type, key);
        if (bytes == null) {
            return null;
        } else {
            return new String(bytes);
        }
    }

    /**
     * 得到计数器数值
     *
     * @param type
     * @param key
     */
    public long getCounter(final String type, final String key) {
        return new Executor<Long>() {
            @Override
            Long execute() {
                try {
                    byte[] v = jedis.get(getCacheName(type, key));
                    if (v != null) {
                        // return NumberUtil.parseLong(new String(v));
                        return Long.valueOf(new String(v));
                    } else {
                        return 0l;
                    }
                } catch (Exception e) {
                    logger.error("cache " + getCacheName(type, key) + " error");
                }
                return 0l;
            }
        }.getResult();
    }

    abstract class Executor<T> {
        /**
         * 回调
         * @return 执行结果
         */
        abstract T execute();

        /**
         * 调用{@link #execute()}并返回执行结果
         * 它保证在执行{@link #execute()}之后释放数据源returnResource(jedis)
         *
         * @return 执行结果
         */
        public T getResult() {
            T result = null;
            try {
                result = execute();
            } catch (Throwable e) {
                throw new RuntimeException("Redis execute exception", e);
            } finally {
				/*
				if(jedis != null){
					try {
						jedis.close();
					} catch (IOException e) {
						logger.error("jedis cluster 关闭异常：",e);
					}
				}*/
            }
            return result;
        }
    }

    /**
     * 键值对
     *
     * @param <K>
     *            key
     * @param <V>
     *            value
     * @author fengjc
     * @version V1.0
     */
    public class Pair<K, V> {

        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
