package com.madiot.poke.codec.api;

import java.util.Date;

/**
 * Created by julian on 2017/8/19.
 */
public interface INoticeMessage extends IDecodeAble, IEncodeAble {

    Long getId();

    Date getTimestamp();

    ICommandType getType();

    INoticeData getData();


}
