/**
 * @Title: LadgtScoreRule.java
 * @Package com.madiot.poke.ladgt.rule.score
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 * @version
 */
package com.madiot.poke.ladgt.rule.score;

import com.madiot.poke.api.rule.IScoreRule;
import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;

/**
 * @ClassName: LadgtScoreRule
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/23
 */
public class LadgtScoreRule implements IScoreRule<LadgtRoleEnum> {

    public int getScore(LadgtRoleEnum role, LadgtRoleEnum winner) {
        if (winner == LadgtRoleEnum.FARMER) {
            if (role == LadgtRoleEnum.FARMER) {
                return 2;
            } else {
                return -3;
            }
        } else if (winner == LadgtRoleEnum.HELPER || winner == LadgtRoleEnum.LAND_LORD) {
            if (role != LadgtRoleEnum.FARMER) {
                return 3;
            } else {
                return -2;
            }
        } else if (winner == LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
            if (role == LadgtRoleEnum.LAND_LORD_WITH_HELPER) {
                return 8;
            } else {
                return -2;
            }
        }
        return 0;
    }
}
