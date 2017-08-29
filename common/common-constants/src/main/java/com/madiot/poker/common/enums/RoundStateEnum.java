package com.madiot.poker.common.enums;

/**
 * Created by DELL on 2017/5/7.
 */
public enum RoundStateEnum {

    /**
     * 等待阶段
     */
    WAIT,
    /**
     * 发牌阶段
     */
    DEAL,
    /**
     * 叫地主阶段(自动分配)
     */
    CALL_LANDLORD,
    /**
     * 叫暗地主阶段
     */
    CALL_HELPER,
    /**
     * 出牌阶段
     */
    PLAY,
    /**
     * 牌局结束阶段
     */
    FINISH;

    /**
     * 获取牌局下一阶段状态
     * @param roundState 当前牌局状态
     * @return
     */
    public static RoundStateEnum getNext(RoundStateEnum roundState) {
        if (roundState == null) {
            return WAIT;
        } else if (roundState == WAIT) {
            return DEAL;
        } else if (roundState == DEAL) {
            return CALL_HELPER;
        } else if (roundState == CALL_HELPER) {
            return PLAY;
        }
        return null;
    }
}
