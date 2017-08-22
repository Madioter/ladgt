/**
 * @Title: LadgtPlayRoundProcess.java
 * @Package com.madiot.poke.ladgt.play.round
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.process;

import com.madiot.poke.api.IPlayRoundProcess;
import com.madiot.poke.api.IProcessLink;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.exception.MustStopProcessException;
import com.madiot.poke.exception.SlightProcessException;

import java.util.ArrayList;
import java.util.List;

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
        processes.add(new LadgtDealProcessLink());
        processes.add(new LadgtCallHelperProcessLink());
        processes.add(new LadgtPlayProcessLink());
        processes.add(new LadgtScoreProcessLink());
    }

    private IPlayRound playRound;

    public LadgtPlayRoundProcess(IPlayRound playRound) {
        this.playRound = playRound;
    }

    public void execute() {
        for (IProcessLink processLink : processes) {
            try {
                processLink.doProcess(playRound);
            } catch (MustStopProcessException e) {
                throw e;
            } catch (SlightProcessException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
