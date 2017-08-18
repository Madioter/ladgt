package com.madioter.poker.doudizhu.liuan.server.observer;

import com.madioter.poker.common.enums.RoundStateEnum;
import com.madioter.poker.common.exception.NeedNewRoundException;
import com.madioter.poker.doudizhu.liuan.domain.PlayerCardDO;
import com.madioter.poker.netty.message.common.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 牌局主题
 * Created by DELL on 2017/5/7.
 */
public class RoundSubject {

    private Random random = new Random();

    public static final int PLAYER = 5;

    private List<PlayerObserver> playerObserverList = new ArrayList<>();

    private int token = 0;

    private List<PlayerCardDO> lastCards;

    /**
     * 默认初始状态为等待
     */
    private RoundStateEnum roundState = RoundStateEnum.WAIT;

    public List<PlayerCardDO> getLastCards() {
        return lastCards;
    }

    public void setLastCards(List<PlayerCardDO> lastCards) {
        this.lastCards = lastCards;
    }

    /**
     * 增加玩家
     *
     * @param playerObserver 玩家
     * @throws NeedNewRoundException 玩家已经满，需要增加新的牌局
     */
    public void addPlayer(PlayerObserver playerObserver) throws NeedNewRoundException {
        synchronized (playerObserverList) {
            if (playerObserverList.size() < 5) {
                playerObserverList.add(playerObserver);
            } else {
                throw new NeedNewRoundException("player is enough, you need join another round");
            }
        }
    }

    public void deal(List<PlayerCardDO> playerCards) {
        getNextObserver().addPlayerCards(playerCards);
    }

    public PlayerObserver getNextObserver() {
        if (token >= playerObserverList.size()) {
            token = 0;
        }
        PlayerObserver observer = playerObserverList.get(token);
        token++;
        while (observer.getRole() == RoleEnum.BYSTANDER) {
            observer = getNextObserver();
        }
        return observer;
    }

    public void callLandlorder() {

    }

    public void setIndex(int index) {
        this.token = index;
    }

    public void restart() {
        token = 0;
        lastCards = null;
        for (PlayerObserver observer : playerObserverList) {
            observer.restart();
        }
    }

    public RoundStateEnum getRoundState() {
        return this.roundState;
    }

    public Random getRandom() {
        return random;
    }
}
