/**
 * @Title: Configuration.java
 * @Package com.madiot.poke.ladgt.config
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.ladgt.rule.config;

import com.madiot.poke.ladgt.rule.comparator.LadgtComparator;
import com.madiot.poke.api.rule.IPokeTypeComparator;
import com.madiot.poke.api.rule.IPokeTypeRegistry;
import com.madiot.poke.ladgt.rule.poketype.LadgtPokeTypeRegistry;

/**
 * @ClassName: Configuration
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtConfiguration {

    private IPokeTypeRegistry pokeTypeRegistry = new LadgtPokeTypeRegistry();
    private IPokeTypeComparator ladgtComparator = new LadgtComparator();

    public IPokeTypeRegistry getPokeTypeRegistry() {
        return pokeTypeRegistry;
    }

    public void setPokeTypeRegistry(IPokeTypeRegistry pokeTypeRegistry) {
        this.pokeTypeRegistry = pokeTypeRegistry;
    }

    public IPokeTypeComparator getLadgtComparator() {
        return ladgtComparator;
    }

    public void setLadgtComparator(IPokeTypeComparator ladgtComparator) {
        this.ladgtComparator = ladgtComparator;
    }
}
