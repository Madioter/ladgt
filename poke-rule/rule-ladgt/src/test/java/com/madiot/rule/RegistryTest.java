/**
 * @Title: RegistryTest.java
 * @Package com.madiot.rule
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.rule;

import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.ladgt.rule.config.LadgtConfiguration;
import com.madiot.poke.ladgt.rule.poketype.BasePokeTypeRule;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.pool.LadgtPokeCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RegistryTest
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class RegistryTest extends TestCase {

    private LadgtConfiguration configuration = new LadgtConfiguration();

    @Test
    public void testPair() {
        List<LadgtPokeCard> pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        IOneHand oneHand = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand.getPokeType().equals(BasePokeTypeRule.PokeType.PAIR.toString());
    }

    @Test
    public void testSerialPair() {
        List<LadgtPokeCard> pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.THREE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.THREE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FOUR));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FOUR));
        IOneHand oneHand = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand.getPokeType().equals(BasePokeTypeRule.PokeType.SERIAL_PAIR.toString());
    }

    @Test
    public void testSerialThree() {
        List<LadgtPokeCard> pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        IOneHand oneHand = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand.getPokeType().equals(BasePokeTypeRule.PokeType.SERIAL_THREE.toString());
    }

    @Test
    public void testButterfly() {
        List<LadgtPokeCard> pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SIX));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SEVEN));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.SEVEN));
        IOneHand oneHand = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand.getPokeType().equals(BasePokeTypeRule.PokeType.BUTTERFLY.toString());
    }

    @Test
    public void testBomb(){
        List<LadgtPokeCard> pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        LadgtOneHand oneHand = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand.getPokeType().equals(BasePokeTypeRule.PokeType.BOMB.toString());

        pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.DIAMOND, LadgtPokeCard.CardValue.FIVE));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.HEART, LadgtPokeCard.CardValue.FIVE));
        LadgtOneHand oneHand2 = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand2.getPokeType().equals(BasePokeTypeRule.PokeType.BOMB.toString());

        pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        LadgtOneHand oneHand3 = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand3.getPokeType().equals(BasePokeTypeRule.PokeType.BOMB.toString());

        pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.BLACK, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.RED, LadgtPokeCard.CardValue.JOKER));
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.RED, LadgtPokeCard.CardValue.JOKER));
        LadgtOneHand oneHand4 = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand4.getPokeType().equals(BasePokeTypeRule.PokeType.BOMB.toString());

        pokeCardList = new ArrayList<>();
        pokeCardList.add(new LadgtPokeCard(LadgtPokeCard.CardType.TAGGED, LadgtPokeCard.CardValue.EIGHT));
        LadgtOneHand oneHand5 = new LadgtOneHand(pokeCardList, configuration);
        assert oneHand5.getPokeType().equals(BasePokeTypeRule.PokeType.BOMB.toString());

        assert oneHand.compareTo(oneHand2) < -1;
        assert oneHand3.compareTo(oneHand2) < -1;
        assert oneHand3.compareTo(oneHand4) < -1;
        assert oneHand2.compareTo(oneHand4) < -1;
        assert oneHand4.compareTo(oneHand5) < -1;
    }
}
