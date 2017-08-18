package com.madiot.poke.api.rule;

import com.madiot.poke.errors.ComparatorException;

/**
 * Created by julian on 2017/8/17.
 */
public interface IPokeTypeComparator<T> {

    int compareWith(T target, T source) throws ComparatorException;
}
