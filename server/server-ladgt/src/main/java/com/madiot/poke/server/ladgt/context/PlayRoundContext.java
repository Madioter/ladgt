/**
 * @Title: PlayRoundContext.java
 * @Package com.madiot.poke.server.ladgt.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.server.ladgt.context;

import com.madiot.poke.context.api.IPlayRound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: PlayRoundContext
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class PlayRoundContext {

    private static Map<Integer, IPlayRound> playRoundMap = new ConcurrentHashMap<>();

    public static void put(Integer key, IPlayRound playRound) {
        playRoundMap.put(key, playRound);
    }

    public static IPlayRound get(Integer key) {
        return playRoundMap.get(key);
    }

    public static int nextIndex() {
        return 0;
    }
}
