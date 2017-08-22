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
import com.madiot.poke.context.api.IPlayRound;

/**
 * @ClassName: LadgtPlayProcessLink
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtPlayProcessLink implements IProcessLink {
    @Override
    public IProcessDef getProcessType() {
        return LadgtProcessLinkEnum.PLAY;
    }

    @Override
    public void doProcess(IPlayRound playRound) {

    }
}
