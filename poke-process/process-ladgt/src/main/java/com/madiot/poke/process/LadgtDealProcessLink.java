package com.madiot.poke.process;

import com.madiot.poke.api.IProcessLink;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;

import java.util.List;

/**
 * Created by julian on 2017/8/19.
 */
public class LadgtDealProcessLink implements IProcessLink {

    @Override
    public LadgtProcessLinkEnum getProcessType() {
        return LadgtProcessLinkEnum.DEAL;
    }

    @Override
    public void doProcess(IPlayRound playRound) {
        playRound.deal();
    }
}
