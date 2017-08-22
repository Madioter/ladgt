package com.madiot.poke.context.config;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCardFactory;
import com.madiot.poke.api.rule.IPokeTypeComparator;
import com.madiot.poke.api.rule.IPokeTypeRegistry;
import com.madiot.poke.codec.api.INoticeDataFactory;
import com.madiot.poke.server.api.IPokeMessageServer;

/**
 * Created by julian on 2017/8/19.
 */
public interface IConfiguration<T extends IOneHand> {

    IPokeTypeRegistry getPokeTypeRegistry();

    IPokeTypeComparator<T> getComparator();

    IPokeCardFactory getPokeCardFactory();

    INoticeDataFactory getNoticeDataFactory();

    IPokeMessageServer getPokeMessageServer();
}
