/**
 * @Title: BasePokeTypeRule.java
 * @Package com.madiot.poke.rule.landegot.poketype
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.rule.poketype;

import com.madiot.poke.api.rule.IPokeCard;
import com.madiot.poke.api.rule.IPokeTypeRule;

import java.util.List;

/**
 * @ClassName: BasePokeTypeRule
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public abstract class BasePokeTypeRule implements IPokeTypeRule {

    public enum PokeType {
        SINGLE, PAIR, SERIAL_PAIR, SERIAL_THREE, BUTTERFLY, BOMB;
    }
}
