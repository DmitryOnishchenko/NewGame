package com.electdead.newgame.engine;

import java.util.ArrayList;

public class FastRemoveArrayList<E> extends ArrayList<E> {
    public FastRemoveArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public E remove(int index) {
        E item = get(size()-1);
        set(index, item);
        return super.remove(size()-1);
    }
}
