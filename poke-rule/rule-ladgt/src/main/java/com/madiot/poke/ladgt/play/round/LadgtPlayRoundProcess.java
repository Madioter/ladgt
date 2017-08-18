/**
 * @Title: LadgtPlayRoundProcess.java
 * @Package com.madiot.poke.ladgt.play.round
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.play.round;

import com.madiot.poke.api.play.INoticeMessage;
import com.madiot.poke.api.play.IPlayObserver;
import com.madiot.poke.api.play.IPlayRound;
import com.madiot.poke.api.play.IPlayRoundProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: LadgtPlayRoundProcess
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtPlayRoundProcess implements IPlayRoundProcess {

    private static final List<IProcessLink> processes;

    static {
        processes = new ArrayList<>();
        processes.add(new IProcessLink() {

            private static final int READY_TIMEOUT = 15;

            @Override
            public ProcessLinkEnum getProcessType() {
                return ProcessLinkEnum.READY;
            }

            @Override
            public ProcessLinkEnum doProcess(IPlayRound playRound) {
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
                return ProcessLinkEnum.DEAL;
            }

            @Override
            public void timeoutProcess(IPlayRound playRound) {
                playRound.end();
            }
        });
    }

    public static interface IProcessLink {

        ProcessLinkEnum getProcessType();

        ProcessLinkEnum doProcess(IPlayRound playRound);

        void timeoutProcess(IPlayRound playRound);
    }

    public static enum ProcessLinkEnum {
        READY, DEAL, CALL_LANDLORD, PLAY, END;
    }
}
