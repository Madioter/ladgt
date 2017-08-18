package com.madiot.poker.common;

import com.madioter.poker.common.CardType;
import com.madioter.poker.common.PokerCard;
import com.madioter.poker.common.PokerTypeEnum;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.common.cardtype.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 2017/5/20.
 */
public class CardTypeTest extends TestCase {


    private List<PokerCard> getSimpleCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        cards.add(card);
        return cards;
    }

    @Test
    public void testSimpleCard() {
        CardType cardType = CardType.get(getSimpleCard());
        assert cardType instanceof SimpleCardType;
        assert cardType.getIndex() == CardType.getIndex(PokerValueEnum.V_6);
    }

    private List<PokerCard> getPairsCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        cards.add(card);
        cards.add(card1);
        return cards;
    }

    @Test
    public void testPairsCard() {
        CardType cardType = CardType.get(getPairsCard());
        assert cardType instanceof PairsCardType;
        assert cardType.getIndex() == CardType.getIndex(PokerValueEnum.V_6);
    }

    @Test
    public void testThreeCard() {
        CardType cardType = CardType.get(getThreeCard());
        assert cardType instanceof ThreeCardType;
        assert cardType.getIndex() == CardType.getIndex(PokerValueEnum.V_6);
    }

    private List<PokerCard> getThreeCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card2 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_6);

        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        return cards;
    }

    @Test
    public void testThreeJokerCard() {
        CardType cardType = CardType.get(getThreeJokerCard());
        assert cardType instanceof BombCardType;
    }

    private List<PokerCard> getThreeJokerCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.JOKER, PokerValueEnum.BLACK_JOKER);
        PokerCard card1 = new PokerCard(PokerTypeEnum.JOKER, PokerValueEnum.BLACK_JOKER);
        PokerCard card2 = new PokerCard(PokerTypeEnum.JOKER, PokerValueEnum.BLACK_JOKER);

        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        return cards;
    }

    @Test
    public void testBombCard() {
        CardType cardType = CardType.get(getBombCard());
        assert cardType instanceof BombCardType;
        assert cardType.compareTo(CardType.get(getThreeJokerCard())) < 0;
    }

    private List<PokerCard> getBombCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card2 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_6);
        PokerCard card3 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_6);

        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        return cards;
    }

    @Test
    public void testThreeTwoCard() {
        CardType cardType = CardType.get(getThreeTwoCard());
        assert cardType instanceof ThreeTwoCardType;
    }

    private List<PokerCard> getThreeTwoCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card2 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_6);
        PokerCard card3 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card4 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);

        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        return cards;
    }

    @Test
    public void testSerialThreeCard() {
        CardType cardType = CardType.get(getSerialThreeCard());
        assert cardType instanceof SerialThreeCardType;
    }

    private List<PokerCard> getSerialThreeCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card2 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_6);
        PokerCard card3 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card4 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card5 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);

        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        return cards;
    }

    @Test
    public void testSerialTwoCard() {
        CardType cardType = CardType.get(getSerialTwoCard());
        assert cardType instanceof SerialPairsCardType;
    }

    private List<PokerCard> getSerialTwoCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card2 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card3 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card4 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_4);
        PokerCard card5 = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_4);
        PokerCard card6 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_3);
        PokerCard card7 = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_3);


        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        return cards;
    }

    @Test
    public void testButterflyCard() {
        CardType cardType = CardType.get(getButterflyCard());
        assert cardType instanceof ButterflyCardType;
    }

    private List<PokerCard> getButterflyCard() {
        List<PokerCard> cards = new ArrayList<>();
        PokerCard card = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_6);
        PokerCard card1 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card2 = new PokerCard(PokerTypeEnum.CLUBS, PokerValueEnum.V_6);
        PokerCard card3 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card4 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card5 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_5);
        PokerCard card6 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_4);
        PokerCard card7 = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_4);
        PokerCard card8 = new PokerCard(PokerTypeEnum.HEARTS, PokerValueEnum.V_3);
        PokerCard card9 = new PokerCard(PokerTypeEnum.DIAMODS, PokerValueEnum.V_3);


        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        cards.add(card8);
        cards.add(card9);
        return cards;
    }
}
