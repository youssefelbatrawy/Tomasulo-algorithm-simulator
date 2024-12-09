package com.tomasulo.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class FixedSizeLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int maxSize;

    public FixedSizeLinkedHashMap(int maxSize) {
        super(maxSize, 0.75f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}