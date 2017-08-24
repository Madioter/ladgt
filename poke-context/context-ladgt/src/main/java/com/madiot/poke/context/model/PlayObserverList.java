/**
 * @Title: PlayObserverList.java
 * @Package com.madiot.poke.context
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/22
 * @version
 */
package com.madiot.poke.context.model;

import com.madiot.poke.context.api.IPlayObserver;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName: PlayObserverList
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/22
 */
public class PlayObserverList<T extends IPlayObserver> extends ArrayList<T> {

    private int index = 0;

    private int offset = 0;

    public PlayObserverList(Collection<T> collection) {
        this.addAll(collection);
    }

    public void markRound() {
        this.index = this.index + this.offset;
        if (this.index >= size()) {
            this.index = this.index - size();
        }
        this.offset = 0;
    }

    public IPlayObserver getNext() {
        this.offset++;
        if (this.offset >= size()) {
            return null;
        }
        int currentIndex = this.index + this.offset;
        if (currentIndex >= size()) {
            return get(currentIndex - size());
        } else {
            return get(currentIndex);
        }
    }

    public boolean hasNext() {
        if (this.offset >= size() - 1) {
            return false;
        }
        return true;
    }
}
