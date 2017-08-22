/**
 * @Title: LadgtResultFactory.java
 * @Package com.madiot.poke.codec.ladgt
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt;

import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.api.IResultFactory;

import java.util.List;

/**
 * @ClassName: LadgtResultFactory
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtResultFactory implements IResultFactory {

    @Override
    public INoticeResult getResult(int resultType) {
        return LadgtNoticeResultEnum.get(resultType);
    }
}
