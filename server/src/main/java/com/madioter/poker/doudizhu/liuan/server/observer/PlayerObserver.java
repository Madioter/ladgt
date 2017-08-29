package com.madioter.poker.doudizhu.liuan.server.observer;

import com.madiot.poker.common.future.CallbackFuture;
import com.madioter.poker.netty.message.common.enums.RoleEnum;
import com.madioter.poker.doudizhu.liuan.domain.PlayerCardDO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 玩家观察者
 * Created by DELL on 2017/5/7.
 */
public class PlayerObserver {

    private List<PlayerCardDO> playerCardList = new ArrayList<>();

    private RoleEnum role;

    public void addPlayerCards(List<PlayerCardDO> playerCards) {
        playerCardList.addAll(playerCards);
    }

    /**
     * 叫地主
     */
    public CallbackFuture<Boolean> callLandlorder() {
        return null;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public boolean haveRole() {
        return role != null;
    }

    public void restart() {
        playerCardList.clear();
        role = null;
    }

    public RoleEnum getRole() {
        return this.role;
    }

    /**
     * 出牌
     *
     * @return
     */
    public CallbackFuture<Boolean> playcard() {
        return null;
    }

    public CallbackFuture<Integer> callHelper() {
        return null;
    }

    public int getFristHelperCard() {
        Collections.sort(playerCardList);
        PlayerCardDO current = null;
        for (PlayerCardDO card : playerCardList) {
            if (current == null) {
                current = card;
            }
            if (current.getCardIndex() == card.getCardIndex()) {
                return current.getCardIndex();
            }
        }
        return 0;
    }

    public boolean checkHelperCardIndex(int playerCardIndex) {
        int size = 0;
        for (PlayerCardDO card : playerCardList) {
            if (card.getCardIndex() == playerCardIndex) {
                size++;
            }
        }
        if (size <= 1) {
            return false;
        }
        return true;
    }

    public boolean isHelper(int playerCardIndex) {
        for (PlayerCardDO playerCard : playerCardList) {
            if (playerCard.getCardIndex() == playerCardIndex) {
                this.role = RoleEnum.HELPER;
                // 发送角色改变
                return true;
            }
        }
        return  false;
    }
}
