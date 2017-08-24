/**
 * @Title: IPokeRedisService.java
 * @Package com.madiot.common.redis.service
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.service;

import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.common.redis.model.RoundInfoDO;

/**
 * @ClassName: IPokeRedisService
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public interface IPokeRedisService {

    ConnectInfoDO getConnectInfo(Integer playerId);

    void saveConnectInfo(ConnectInfoDO connectInfo);

    RoundInfoDO getRoundInfo(Integer roundIndex);

    void saveRoundInfo(RoundInfoDO roundInfo);
}
