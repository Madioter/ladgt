/**
 * @Title: TypeList.java
 * @Package com.igdata.gbparser.common.type
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/4/5
 * @version
 */
package com.madioter.poker.netty.message.type.impl;

import com.madioter.poker.common.exception.ParserException;

import java.util.ArrayList;

/**
 * @ClassName: TypeList
 * @Description: 带数据类型定义的列表
 * @author Yi.Wang2
 * @date 2017/4/5
 */
public class TypeList<E> extends ArrayList<E> {

    private Class<E> type;

    public TypeList(Class<E> type) {
        this.type = type;
    }

    public E getInstance() throws ParserException {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ParserException("生成对象实例失败" + type.getName());
        }
    }
}
