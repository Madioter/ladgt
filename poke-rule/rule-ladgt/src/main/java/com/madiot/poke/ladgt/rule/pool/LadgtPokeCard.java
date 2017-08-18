/**
 * @Title: LadgtPokeCard.java
 * @Package com.madiot.poke.rule.landegot.pool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.rule.pool;

import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.errors.ComparatorException;

/**
 * @ClassName: LadgtPokeCard
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtPokeCard implements IPokeCard {

    private CardType cardType;

    private CardValue cardValue;

    public LadgtPokeCard(CardType cardType, CardValue cardValue) {
        this.cardType = cardType;
        this.cardValue = cardValue;
    }

    @Override
    public String toString() {
        return cardType + "_" + cardValue;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof LadgtPokeCard && ((LadgtPokeCard) obj).cardType == this.cardType
                && ((LadgtPokeCard) obj).cardValue == this.cardValue;
    }

    @Override
    public ICardValue getValue() {
        return cardValue;
    }

    @Override
    public ICardType getType() {
        return cardType;
    }

    /**
     * @ClassName: Card
     * @Description: TODO
     * @author Yi.Wang2
     * @date 2017/8/17
     */
    public static enum CardType implements ICardType {
        HEART, SPADE, DIAMOND, CLUB, BLACK, RED, TAGGED;
    }

    public static enum CardValue implements ICardValue {
        THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NIGHT, TEN, JACK, QUEEN, KING, ONE, TWO, JOKER;

        public boolean lessThen(ICardValue key) {
            if (key instanceof CardValue) {
                return this.ordinal() < ((CardValue) key).ordinal();
            }
            throw new ComparatorException("value type can't be compare, class:[" + key.getClass().getName() + "," + this.getClass().getName() + "]");
        }

        public ICardValue before() {
            if (this.lessThen(CardValue.TWO)) {
                if (this.ordinal() > 0) {
                    return CardValue.values()[this.ordinal() - 1];
                } else {
                    return null;
                }
            }
            return null;
        }
    }
}
