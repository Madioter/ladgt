package com.madioter.poker.doudizhu.liuan.domain;

/**
 * Created by DELL on 2017/5/7.
 */
public class PlayerCardDO implements Comparable<PlayerCardDO> {

    private int cardIndex;

    private long pokerId;

    public int getCardIndex() {
        return cardIndex;
    }

    public long getPokerId() {
        return pokerId;
    }

    public PlayerCardDO(int cardIndex, long pokerId) {
        this.cardIndex = cardIndex;
        this.pokerId = pokerId;
    }

    public String encode() {
        return cardIndex + ":" + pokerId;
    }

    public static PlayerCardDO getInstance(String data) {
        String[] items = data.split(":");
        PlayerCardDO playerCard = new PlayerCardDO(Integer.valueOf(items[0]), Long.valueOf(items[1]));
        return playerCard;
    }

    @Override
    public int compareTo(PlayerCardDO o) {
        return Integer.valueOf(o.cardIndex).compareTo(this.cardIndex);
    }
}
