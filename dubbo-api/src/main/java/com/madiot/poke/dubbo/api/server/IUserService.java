package com.madiot.poke.dubbo.api.server;

import com.madiot.poker.common.domain.IPlayer;

/**
 * Created by julian on 2017/8/30.
 */
public interface IUserService {

    IPlayer login(String username,String password);
}
