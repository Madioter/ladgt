package com.madiot.poke.api;

import com.madiot.poke.context.api.IPlayRound;

/**
 * Created by julian on 2017/8/19.
 */
public interface IProcessLink {

    IProcessDef getProcessType();

    IProcessDef doProcess(IPlayRound playRound);

    void timeoutProcess(IPlayRound playRound);
}
