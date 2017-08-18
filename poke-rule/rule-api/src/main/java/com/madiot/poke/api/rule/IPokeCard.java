package com.madiot.poke.api.rule;

import javax.sound.sampled.AudioFileFormat;

/**
 * Created by julian on 2017/8/17.
 */
public interface IPokeCard {

    ICardValue getValue();

    ICardType getType();

    public interface ICardValue {
        boolean lessThen(ICardValue key);

        ICardValue before();
    }

    public interface ICardType {

    }
}
