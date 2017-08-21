package com.madiot.poke.codec.api;

/**
 * Created by julian on 2017/8/19.
 */
public interface ICommandFactory {

    public ICommandType getCommandType(int commandType);
}
