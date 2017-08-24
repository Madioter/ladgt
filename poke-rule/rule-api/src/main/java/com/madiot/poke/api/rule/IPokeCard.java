package com.madiot.poke.api.rule;

/**
 * Created by julian on 2017/8/17.
 */
public interface IPokeCard {

    ICardValue getValue();

    ICardType getType();

    public interface ICardValue {

        boolean lessThen(ICardValue key);

        int compareWith(ICardValue value);

        ICardValue before();
    }

    public interface ICardType {

    }
}
