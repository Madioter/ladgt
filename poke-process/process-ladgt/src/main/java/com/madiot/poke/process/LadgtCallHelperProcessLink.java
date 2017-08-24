/**
 * @Title: LadgtCallHelperProcessLink.java
 * @Package com.madiot.poke.process
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.process;

import com.madiot.poke.api.IProcessDef;
import com.madiot.poke.api.IProcessLink;
import com.madiot.poke.codec.ladgt.LadgtCommandTypeEnum;
import com.madiot.poke.codec.ladgt.notices.downstream.CallHelper;
import com.madiot.poke.codec.message.NoticeMessage;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.context.exception.ContextException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: LadgtCallHelperProcessLink
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtCallHelperProcessLink implements IProcessLink {

    private static final int CALL_HELPER_TIME_OUT = 15;

    @Override
    public IProcessDef getProcessType() {
        return LadgtProcessLinkEnum.CALL_HELPER;
    }

    @Override
    public void doProcess(IPlayRound playRound) {
        playRound.callHelper(CALL_HELPER_TIME_OUT);
    }
}
