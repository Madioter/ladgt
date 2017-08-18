/**
 * @Title: IPokeCardPool.java
 * @Package com.madiot.poke.api.rule
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.api.rule;

import com.madiot.poke.api.play.IDistributional;

/**
 * @ClassName: IPokeCardPool
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public interface IPokeCardFactory {

    void distributional(IDistributional distributional);
}
