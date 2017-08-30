package com.madiot.poke.server.base;

import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;

/**
 * Created by julian on 2017/8/29.
 */
public interface IProcessExecute<T> {

    byte[] execute(T message);
}
