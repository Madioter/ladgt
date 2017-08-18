package com.madioter.poker.doudizhu.liuan.server.service;

import com.madioter.poker.doudizhu.liuan.server.observer.RoundSubject;

/**
 * Created by DELL on 2017/5/8.
 */
public interface IPokerService {

    /**
     * 发牌
     * @param roundSubject
     */
    public void deal(RoundSubject roundSubject);

    /**
     * 叫暗地主
     * @param roundSubject
     */
    public void callHelper(RoundSubject roundSubject);

    /**
     * 叫地主
     * @param roundSubject
     */
    public void callLandlorder(RoundSubject roundSubject);

    /**
     * 出牌
     * @param roundSubject
     */
    public void playCard(RoundSubject  roundSubject);
}
