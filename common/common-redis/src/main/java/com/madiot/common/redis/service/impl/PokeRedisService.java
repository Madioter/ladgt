/**
 * @Title: PokeRedisService.java
 * @Package com.madiot.common.redis.service
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.service.impl;

import com.madiot.common.redis.component.IRedisCache;
import com.madiot.common.redis.constants.RedisKeyConstant;
import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.common.redis.model.RoundInfoDO;
import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.common.utils.json.JsonUtils;
import com.madiot.common.utils.string.StringUtils;
import com.madioter.poker.common.exception.ConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: PokeRedisService
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
@Service
public class PokeRedisService implements IPokeRedisService {

    @Autowired
    private IRedisCache redisCache;

    @Override
    public ConnectInfoDO getConnectInfo(Integer playerId) {
        String connectInfoStr = redisCache.getString(RedisKeyConstant.PLAYER_CONNECT_INFO_KEY, String.valueOf(playerId));
        if (StringUtils.isEmpty(connectInfoStr)) {
            throw new ConnectionException("请求处理异常，Redis找不到通道的连接信息，playerId:" + playerId);
        }
        ConnectInfoDO connectInfo = JsonUtils.toJavaBean(connectInfoStr, ConnectInfoDO.class);
        return connectInfo;
    }

    @Override
    public void saveConnectInfo(ConnectInfoDO connectInfo) {
        redisCache.putInCache(RedisKeyConstant.PLAYER_CONNECT_INFO_KEY, connectInfo.getPlayerId(), JsonUtils.toJsonString(connectInfo),
                RedisKeyConstant.PLAYER_CONNECT_INFO_SECOND);
    }

    @Override
    public RoundInfoDO getRoundInfo(Integer roundIndex) {
        String roundInfoStr = redisCache.getString(RedisKeyConstant.ROUND_INFO_KEY, String.valueOf(roundIndex));
        if (StringUtils.isEmpty(roundInfoStr)) {
            throw new ConnectionException("请求处理异常，Redis找不到通道的连接信息，roundIndex:" + roundIndex);
        }
        RoundInfoDO roundInfo = JsonUtils.toJavaBean(roundInfoStr, RoundInfoDO.class);
        return roundInfo;
    }

    @Override
    public void saveRoundInfo(RoundInfoDO roundInfo) {
        redisCache.putInCache(RedisKeyConstant.ROUND_INFO_KEY, roundInfo.getRoundIndex(), JsonUtils.toJsonString(roundInfo),
                RedisKeyConstant.ROUND_INFO_SECOND);
    }


}
