package com.madiot.poke.api.rule;

import java.util.List;

/**
 * Created by julian on 2017/8/17.
 */
public interface IPokeTypeRule {

    String getType();

    <T extends IPokeCard> boolean check(IOneHand<T> oneHand);
}
