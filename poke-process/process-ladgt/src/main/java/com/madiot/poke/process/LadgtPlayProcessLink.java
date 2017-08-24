/**
 * @Title: LadgtPlayProcessLink.java
 * @Package com.madiot.poke.process
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.process;

import com.madiot.poke.api.IProcessDef;
import com.madiot.poke.api.IProcessLink;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: LadgtPlayProcessLink
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayProcessLink implements IProcessLink {

    private static final int DISCARD_TIME_OUT_SECOND = 15;

    @Override
    public IProcessDef getProcessType() {
        return LadgtProcessLinkEnum.PLAY;
    }

    @Override
    public void doProcess(IPlayRound playRound) {
        while (!playRound.isEnd()) {
            playRound.round(DISCARD_TIME_OUT_SECOND);
        }
    }
}
