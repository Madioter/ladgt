package com.madiot.poke.api.play;

import java.util.List;

/**
 * Created by julian on 2017/8/17.
 */
public interface IPlayHandler {

    byte[] serialize();

    void deserialize(byte[] data);
}
