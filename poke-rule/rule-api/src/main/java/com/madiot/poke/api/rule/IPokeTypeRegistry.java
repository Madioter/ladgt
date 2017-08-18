package com.madiot.poke.api.rule;

import java.util.List;

/**
 * Created by julian on 2017/8/17.
 */
public interface IPokeTypeRegistry {

    void register(String pokeType, IPokeTypeRule pokeTypeRule);

    <T extends IPokeTypeRule> void register(Class<T> typeHandlerClass);

    <T extends IPokeTypeRule> void register(String pokeType, Class<T> pokeTypeRule);

    IPokeTypeRule getType(String type);

    <T extends IPokeCard> IPokeTypeRule getType(IOneHand<T> oneHand);

}
