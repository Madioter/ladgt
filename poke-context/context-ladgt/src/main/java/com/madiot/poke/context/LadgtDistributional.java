/**
 * @Title: LadgtDistibutional.java
 * @Package com.madiot.poke.ladgt.play.round
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.context;

import com.madiot.poke.context.api.IDistributional;
import com.madiot.poke.ladgt.rule.config.LadgtConstants;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: LadgtDistibutional
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtDistributional implements IDistributional<LadgtPokeCard> {

    private List<Integer> usedIndex = new ArrayList();

    private Random random = new Random();

    private List<List<LadgtPokeCard>> playerCardList = new ArrayList<>();

    private List<LadgtPokeCard> lastCardList = new ArrayList<>();

    private int landlordIndex;

    private LadgtPokeCard landlordCard;

    @Override
    public void addCard(LadgtPokeCard pokeCard) {
        lastCardList.add(pokeCard);
        if (lastCardList.size() == LadgtConstants.PLAYER_CARDS) {
            playerCardList.add(new ArrayList<>(lastCardList));
            lastCardList.clear();
        }
    }

    @Override
    public Integer getNextIndex(int total) {
        Integer randomIndex = random.nextInt(LadgtConstants.MAX_CARDS);
        while (usedIndex.contains(randomIndex)) {
            randomIndex++;
            if (randomIndex >= LadgtConstants.MAX_CARDS) {
                return null;
            }
        }
        return randomIndex;
    }

    @Override
    public List<LadgtPokeCard> getNextList(int index) {
        if (playerCardList.size() > index) {
            return playerCardList.get(index);
        } else {
            return lastCardList;
        }
    }

    @Override
    public int landlordIndex(int total) {
        return random.nextInt(total);
    }

    @Override
    public void setLandLord(LadgtPokeCard pokeCard) {
        this.landlordIndex = playerCardList.size() - 1;
        this.landlordCard = pokeCard;
    }

    public LadgtPokeCard getLandlordCard() {
        return landlordCard;
    }

    public int getLandlordIndex() {
        return landlordIndex;
    }
}
