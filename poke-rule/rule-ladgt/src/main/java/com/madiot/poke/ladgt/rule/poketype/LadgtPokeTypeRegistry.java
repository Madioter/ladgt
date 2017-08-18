/**
 * @Title: ladgtPokeTypeRegistry.java
 * @Package com.madiot.poke.rule.ladgt.poketype
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype;

import com.madiot.common.reflect.MetaClass;
import com.madiot.poke.api.rule.IOneHand;
import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.api.rule.IPokeTypeRegistry;
import com.madiot.poke.api.rule.IPokeTypeRule;
import com.madiot.poke.ladgt.rule.poketype.type.BombType;
import com.madiot.poke.ladgt.rule.poketype.type.ButteryFlyType;
import com.madiot.poke.ladgt.rule.poketype.type.PairType;
import com.madiot.poke.ladgt.rule.poketype.type.SerialPairType;
import com.madiot.poke.ladgt.rule.poketype.type.SerialThreeType;
import com.madiot.poke.ladgt.rule.poketype.type.SingleCard;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ladgtPokeTypeRegistry
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtPokeTypeRegistry implements IPokeTypeRegistry {

    private Map<String, IPokeTypeRule> ALL_POKE_TYPES = new LinkedHashMap<>();

    public LadgtPokeTypeRegistry() {
        register(BasePokeTypeRule.PokeType.BOMB.toString(), new BombType());
        register(BasePokeTypeRule.PokeType.SINGLE.toString(), new SingleCard());
        register(BasePokeTypeRule.PokeType.PAIR.toString(), new PairType());
        register(BasePokeTypeRule.PokeType.SERIAL_PAIR.toString(), new SerialPairType());
        register(BasePokeTypeRule.PokeType.SERIAL_THREE.toString(), new SerialThreeType());
        register(BasePokeTypeRule.PokeType.BUTTERFLY.toString(), new ButteryFlyType());
    }

    @Override
    public void register(String pokeType, IPokeTypeRule pokeTypeRule) {
        ALL_POKE_TYPES.put(pokeType, pokeTypeRule);
    }

    @Override
    public <T extends IPokeTypeRule> void register(Class<T> pokeTypeRule) {
        IPokeTypeRule pokeType = MetaClass.newInstance(pokeTypeRule).getInstance();
        register(pokeType.getType(), pokeType);
    }

    @Override
    public <T extends IPokeTypeRule> void register(String pokeTypeName, Class<T> pokeTypeRule) {
        IPokeTypeRule pokeType = MetaClass.newInstance(pokeTypeRule).getInstance();
        register(pokeTypeName, pokeType);
    }

    @Override
    public IPokeTypeRule getType(String type) {
        return ALL_POKE_TYPES.get(type);
    }

    @Override
    public <T extends IPokeCard> IPokeTypeRule getType(IOneHand<T> oneHand) {
        for (IPokeTypeRule typeRule : ALL_POKE_TYPES.values()) {
            if (typeRule.check(oneHand)) {
                return typeRule;
            }
        }
        return null;
    }
}
