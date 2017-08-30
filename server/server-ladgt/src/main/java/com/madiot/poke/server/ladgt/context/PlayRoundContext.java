/**
 * @Title: PlayRoundContext.java
 * @Package com.madiot.poke.server.ladgt.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poke.server.ladgt.context;

import com.madiot.common.redis.service.IPokeRedisService;
import com.madiot.poke.context.LadgtPlayRound;
import com.madiot.poke.context.api.IPlayObserver;
import com.madiot.poke.context.api.IPlayRound;
import com.madiot.poke.context.config.IConfiguration;
import com.madiot.poke.context.config.LadgtConfiguration;
import com.madiot.poke.context.model.LadgtPlayer;
import com.madiot.poke.context.observer.LadgtPlayObserver;
import com.madiot.poke.ladgt.rule.config.LadgtConstants;
import com.madiot.poker.common.future.CallbackFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Yi.Wang2
 * @ClassName: PlayRoundContext
 * @Description: TODO
 * @date 2017/8/24
 */
@Service
public class PlayRoundContext {

    @Autowired
    private IPokeRedisService redisService;

    @Resource
    private LadgtConfiguration configuration;

    private Map<Integer, IPlayRound> playRoundMap = new ConcurrentHashMap<>();

    private BlockingQueue<WaitRoundFuture> waitPlayers = new LinkedBlockingDeque<>(LadgtConstants.PLAYER_NUMBER);

    public void put(Integer key, IPlayRound playRound) {
        playRoundMap.put(key, playRound);
    }

    public IPlayRound get(Integer key) {
        return playRoundMap.get(key);
    }

    public CallbackFuture<Integer> addPlayer(int playerId) {
        WaitRoundFuture future = new WaitRoundFuture(playerId);
        while (!waitPlayers.offer(future)) {
            clearBlockingQueue();
        }
        return future;
    }

    private void clearBlockingQueue() {
        Integer roundIndex = redisService.increaseRoundIndex();
        List<LadgtPlayObserver> observers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                WaitRoundFuture callback = waitPlayers.take();
                callback.completed(roundIndex);
                observers.add(new LadgtPlayObserver(new LadgtPlayer(redisService.getConnectInfo(callback.getPlayerId())), null, configuration));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        IPlayRound playRound = new LadgtPlayRound(configuration, observers, roundIndex);
        put(roundIndex, playRound);
    }
}
