/**
 * @Title: IScoreRule.java
 * @Package com.madiot.poke.api.rule
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 * @version
 */
package com.madiot.poke.api.rule;

import com.madiot.poke.codec.common.IRoleEnum;

/**
 * @ClassName: IScoreRule
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 */
public interface IScoreRule<T extends IRoleEnum> {

    public int getScore(T role, T winner);
}
