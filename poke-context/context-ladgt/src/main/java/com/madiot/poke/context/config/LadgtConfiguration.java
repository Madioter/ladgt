/**
 * @Title: Configuration.java
 * @Package com.madiot.poke.ladgt.config
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.context.config;

import com.madiot.common.spring.SpringContextUtils;
import com.madiot.poke.api.rule.IPokeTypeComparator;
import com.madiot.poke.api.rule.IPokeTypeRegistry;
import com.madiot.poke.api.rule.IScoreRule;
import com.madiot.poke.codec.api.INoticeDataFactory;
import com.madiot.poke.codec.ladgt.LadgtNoticeDataFactory;
import com.madiot.poke.context.LadgtPokeCardFactory;
import com.madiot.poke.context.api.IPokeCardFactory;
import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.poke.ladgt.rule.comparator.LadgtComparator;
import com.madiot.poke.ladgt.rule.poketype.LadgtPokeTypeRegistry;
import com.madiot.poke.ladgt.rule.pool.LadgtOneHand;
import com.madiot.poke.ladgt.rule.score.LadgtScoreRule;

/**
 * @ClassName: Configuration
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class LadgtConfiguration implements IConfiguration<LadgtOneHand> {

    private IPokeTypeRegistry pokeTypeRegistry = new LadgtPokeTypeRegistry();

    private IPokeTypeComparator<LadgtOneHand> comparator = new LadgtComparator();

    private IPokeCardFactory pokeCardFactory = new LadgtPokeCardFactory(this);

    private INoticeDataFactory noticeDataFactory = new LadgtNoticeDataFactory();

    private IMessageSendService pokeMessageServer = SpringContextUtils.getBeanByClass(IMessageSendService.class);

    private IScoreRule scoreRule = new LadgtScoreRule();

    public IPokeTypeRegistry getPokeTypeRegistry() {
        return pokeTypeRegistry;
    }

    @Override
    public IPokeTypeComparator<LadgtOneHand> getComparator() {
        return comparator;
    }

    public void setPokeTypeRegistry(IPokeTypeRegistry pokeTypeRegistry) {
        this.pokeTypeRegistry = pokeTypeRegistry;
    }

    public void setComparator(IPokeTypeComparator<LadgtOneHand> comparator) {
        this.comparator = comparator;
    }

    @Override
    public IPokeCardFactory getPokeCardFactory() {
        return pokeCardFactory;
    }

    @Override
    public INoticeDataFactory getNoticeDataFactory() {
        return noticeDataFactory;
    }

    public IMessageSendService getPokeMessageServer() {
        return pokeMessageServer;
    }

    public IScoreRule getScoreRule() {
        return scoreRule;
    }
}
