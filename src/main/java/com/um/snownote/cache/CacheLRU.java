package com.um.snownote.cache;

import java.util.LinkedHashMap;

public class CacheLRU<K,V> extends LinkedHashMap<K,V> {
    private final int capacity;

    public CacheLRU(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K,V> eldest) {
        return size() > capacity;
    }


}
