/**
 * @Title: IPlayRound.java
 * @Package com.madiot.poke.api.play
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context.api;

import com.madiot.poke.codec.message.NoticeMessage;

import java.util.List;

/**
 * @ClassName: IPlayRound
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPlayRound {

    <T extends IPlayObserver> List<T> getPlayers();

    void deal();

    void callHelper(int timeOut);

    boolean isEnd();

    void round(int timeout);

    void score();

    void setReceipt(NoticeMessage noticeMessage);
}
