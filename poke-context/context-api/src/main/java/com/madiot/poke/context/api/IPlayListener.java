/**
 * @Title: IPlayListener.java
 * @Package com.madiot.poke.rule.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context.api;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.context.api.IPlayObserver;

/**
 * @ClassName: IPlayListener
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPlayListener {

    void doListener(IOneHand oneHand, IPlayObserver pokeObserver);

    ListenerOccasion getOccasionType();

    int priority();

    public enum ListenerOccasion {
        BEFORE_PLAY, AFTER_PLAY;
    }
}
