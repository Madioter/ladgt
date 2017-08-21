package com.madiot.poke.codec.api;

import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * Created by julian on 2017/8/19.
 */
public interface IDecodeAble {

    void decode(ByteBuffer buffer);
}
