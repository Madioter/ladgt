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
        processes.add(new LadgtReadyProcessLink());
    }
}
