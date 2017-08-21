package com.madiot.poke.codec.api;

/**
 * Created by julian on 2017/8/20.
 */
public interface INoticeDataFactory {

    INoticeData getInstance(ICommandType commandType, INoticeResult result);
}
