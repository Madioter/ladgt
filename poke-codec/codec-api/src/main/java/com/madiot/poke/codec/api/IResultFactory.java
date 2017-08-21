package com.madiot.poke.codec.api;

/**
 * Created by julian on 2017/8/19.
 */
public interface IResultFactory {

    INoticeResult getResult(int resultType);
}
