/**
 * @Title: IPlayRound.java
 * @Package com.madiot.poke.api.play
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.api.play;

import java.util.List;

/**
 * @ClassName: IPlayRound
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPlayRound {

    <T extends IPlayObserver> List<T> getPlayers();

    void end();
}
