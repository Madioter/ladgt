package com.madiot.poke.process;

import com.madiot.poke.api.IProcessLink;
import com.madiot.poke.api.play.INoticeMessage;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by julian on 2017/8/19.
 */
public class LadgtReadyProcessLink implements IProcessLink {

    private static final int READY_TIMEOUT = 15;

    @Override
    public LadgtProcessLinkEnum getProcessType() {
        return LadgtProcessLinkEnum.READY;
    }

    @Override
    public LadgtProcessLinkEnum doProcess(IPlayRound playRound) {
        List<IPlayObserver> players = playRound.getPlayers();
        List<Future<INoticeMessage>> futures = new ArrayList<>();
        for (IPlayObserver play : players) {
            futures.add(play.ready());
        }
        for (Future<INoticeMessage> future : futures) {
            try {
                future.get(READY_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                timeoutProcess(playRound);
                return null;
            }
        }
        return LadgtProcessLinkEnum.DEAL;
    }

    @Override
    public void timeoutProcess(IPlayRound playRound) {
        playRound.end();
    }
}
