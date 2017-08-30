/**
 * @Title: PlayScore.java
 * @Package com.madiot.poke.context.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 * @version
 */
package com.madiot.poke.context.model;

import com.madiot.poker.common.domain.IPlayer;

/**
 * @ClassName: PlayScore
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 */
public class PlayScore {

    private int playerId;

    private String playerName;

    private int lastScore;

    private int score;

    private boolean winner;

    public PlayScore(IPlayer player, int score, boolean winner) {
        this.playerId = player.getId();
        this.playerName = player.getName();
        this.lastScore = player.getLastScore();
        this.score = score;
        this.winner = winner;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }
}
