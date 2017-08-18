/**
 * @Title: ADeckPoke.java
 * @Package com.madiot.poke.ladgt.rule.pool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.ladgt.rule.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ADeckPoke
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class LadgtDeckPoke extends ArrayList<LadgtPokeCard> {

    private static List<LadgtPokeCard> STATIC_DATA = new ArrayList<>(54);

    private static LadgtPokeCard HEART_EIGHT = new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.EIGHT);

    public static LadgtPokeCard TAGGED_EIGHT = new LadgtPokeCard(LadgtPokeCard.CardType.TAGGED, LadgtPokeCard.CardValue.EIGHT);

    static {
        for (LadgtPokeCard.CardValue value : LadgtPokeCard.CardValue.values()) {
            if (value == LadgtPokeCard.CardValue.EIGHT) {
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.SPADE, value));
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, value));
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.CLUB, value));
            } else if (value == LadgtPokeCard.CardValue.JOKER) {
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, value));
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.RED, value));
            } else {
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, value));
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.SPADE, value));
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, value));
                STATIC_DATA.add(new LadgtPokeCard(LadgtPokeCard.CardType.CLUB, value));
            }
        }
    }

    public LadgtDeckPoke(boolean containTag) {
        addAll(STATIC_DATA);
        if (containTag) {
            add(TAGGED_EIGHT);
        } else {
            add(HEART_EIGHT);
        }
    }
}
