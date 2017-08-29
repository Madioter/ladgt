/**
 * @Title: LadgtNoticeDataFactory.java
 * @Package com.madiot.poke.codec.ladgt
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt;

import com.madiot.common.reflect.MetaClass;
import com.madiot.poke.codec.api.ICommandType;
import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeDataFactory;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.ladgt.notices.downstream.CallHelper;
import com.madiot.poke.codec.ladgt.notices.downstream.Deal;
import com.madiot.poke.codec.ladgt.notices.downstream.Discard;
import com.madiot.poke.codec.ladgt.notices.upstream.Login;
import com.madiot.poke.codec.ladgt.notices.downstream.NoticeDiscard;
import com.madiot.poke.codec.ladgt.notices.downstream.NoticeHelper;
import com.madiot.poke.codec.ladgt.notices.downstream.NoticeScore;
import com.madiot.poke.codec.ladgt.notices.upstream.Ready;
import com.madiot.common.utils.collection.CollectionUtil;
import org.apache.commons.lang.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LadgtNoticeDataFactory
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class LadgtNoticeDataFactory implements INoticeDataFactory {

    private static Map<ICommandType, Class<? extends INoticeData>> ALL_NOTICE_DATA_MAP = new HashMap<>();

    public LadgtNoticeDataFactory() {
        register(LadgtCommandTypeEnum.LOGIN, Login.class);
        register(LadgtCommandTypeEnum.READY, Ready.class);
        register(LadgtCommandTypeEnum.DEAL, Deal.class);
        register(LadgtCommandTypeEnum.CALL_HELPER, CallHelper.class);
        register(LadgtCommandTypeEnum.NOTICE_HELPER, NoticeHelper.class);
        register(LadgtCommandTypeEnum.DISCARD, Discard.class);
        register(LadgtCommandTypeEnum.NOTICE_DISCARD, NoticeDiscard.class);
        register(LadgtCommandTypeEnum.NOTICE_SCORE, NoticeScore.class);
    }

    public void register(ICommandType commandType, Class<? extends INoticeData> clz) {
        ALL_NOTICE_DATA_MAP.put(commandType, clz);
    }

    @Override
    public INoticeData getInstance(ICommandType commandType, INoticeResult result) {
        if (ALL_NOTICE_DATA_MAP.containsKey(commandType)) {
            return MetaClass.newInstance(ALL_NOTICE_DATA_MAP.get(commandType)).getInstance(result);
        }
        return null;
    }

    /**
     * 支持spring的方式导入配置
     * @param dataType 数据类型
     *  <bean id="noticeDataFactory" class="com.madiot.poke.codec.ladgt.LadgtNoticeDataFactory">
     *      <property name="dataType">
     *          <map>
     *              <entry key="LOGIN" value="com.madiot.poke.codec.ladgt.notices.Login"/>
     *              <entry key="READY" value="com.madiot.poke.codec.ladgt.notices.Ready"/>
     *          </map>
     *      </property>
     *  </bean>
     */
    public void setDataType(Map<String, Class<INoticeData>> dataType) {
        if (!CollectionUtil.isEmpty(dataType)) {
            for (Map.Entry<String, Class<INoticeData>> entry : dataType.entrySet()) {
                String key = entry.getKey();
                if (NumberUtils.isNumber(key)) {
                    register(LadgtCommandTypeEnum.get(NumberUtils.toInt(key)), entry.getValue());
                } else {
                    LadgtCommandTypeEnum commandType = LadgtCommandTypeEnum.valueOf(key.toUpperCase());
                    if (commandType != null) {
                        register(commandType, entry.getValue());
                    }
                }
            }
        }
    }
}
