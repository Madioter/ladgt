package com.madioter.poker.doudizhu.liuan.server.service;

import com.madioter.common.redis.IncreaseIdCreator;
import com.madioter.poker.common.PokerDO;
import com.madioter.poker.common.future.CallbackFuture;
import com.madioter.poker.common.constants.Constants;
import com.madioter.poker.netty.message.common.enums.RoleEnum;
import com.madioter.poker.common.enums.RoundStateEnum;
import com.madioter.poker.doudizhu.liuan.domain.PlayerCardDO;
import com.madioter.poker.doudizhu.liuan.server.observer.PlayerObserver;
import com.madioter.poker.doudizhu.liuan.server.observer.RoundSubject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by DELL on 2017/5/8.
 */
@Service
public class LiuanDoudizhuService implements IPokerService {

    /**
     * 发牌
     * @param roundSubject
     */
    @Override
    public void deal(RoundSubject roundSubject) {
        List<PlayerCardDO> playerCardList = new ArrayList<>();
        for (int i = 0; i < Constants.GROUP_SIZE; i++) {
            long pokerId = IncreaseIdCreator.getNextId(Constants.POKER_ID);
            for (int j = 0; j < PokerDO.MAX_INDEX; j++) {
                playerCardList.add(new PlayerCardDO(j, pokerId));
            }
        }
        for (int k = 0; k < roundSubject.PLAYER; k++) {
            for (int i = 0; i < Constants.PLAYER_CARDS; i++) {
                List<PlayerCardDO> playerCards = new ArrayList<>();
                playerCards.add(playerCardList.remove(roundSubject.getRandom().nextInt(playerCards.size())));
                roundSubject.deal(playerCards);
            }
        }
        roundSubject.setLastCards(playerCardList);
    }

    /**
     * 叫暗地主
     * @param roundSubject
     */
    @Override
    public void callHelper(RoundSubject roundSubject) {
        PlayerObserver observer = roundSubject.getNextObserver();
        while (observer.getRole() != RoleEnum.LANDLORDER) {
            observer = roundSubject.getNextObserver();
        }
        CallbackFuture<Integer> future = observer.callHelper();
        int playerCardIndex;
        try {
            playerCardIndex = future.get(15, TimeUnit.SECONDS);
            if (!observer.checkHelperCardIndex(playerCardIndex)) {
                playerCardIndex = observer.getFristHelperCard();
            }
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            playerCardIndex = observer.getFristHelperCard();
        }
        observer = roundSubject.getNextObserver();
        while (observer.getRole() != RoleEnum.LANDLORDER) {
            if (observer.isHelper(playerCardIndex)) {
                return;
            }
        }
    }

    /**
     * 叫地主
     * @param roundSubject
     */
    @Override
    public void callLandlorder(RoundSubject roundSubject) {
        int startIndex = roundSubject.getRandom().nextInt(RoundSubject.PLAYER);
        roundSubject.setIndex(startIndex);
        while(true) {
            PlayerObserver observer = roundSubject.getNextObserver();
            if (!observer.haveRole()) {
                CallbackFuture<Boolean> callbackFuture = observer.callLandlorder();
                try {
                    if (callbackFuture.get(15, TimeUnit.SECONDS)) {
                        observer.setRole(RoleEnum.LANDLORDER);
                        observer.addPlayerCards(roundSubject.getLastCards());
                        break;
                    } else {
                        observer.setRole(RoleEnum.FARMER);
                    }
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    observer.setRole(RoleEnum.FARMER);
                    continue;
                }
            } else {
                roundSubject.restart();
            }
        }
    }

    /**
     * 出牌
     * @param roundSubject
     */
    @Override
    public void playCard(RoundSubject  roundSubject) {
        PlayerObserver observer = roundSubject.getNextObserver();
        while (observer.getRole() != RoleEnum.LANDLORDER) {
            observer = roundSubject.getNextObserver();
        }
        while(roundSubject.getRoundState() != RoundStateEnum.FINISH) {
            CallbackFuture<Boolean> future = observer.playcard();
            try {
                if (future.get(30, TimeUnit.SECONDS)) {
                    observer = roundSubject.getNextObserver();
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                continue;
            }
        }
    }
}
