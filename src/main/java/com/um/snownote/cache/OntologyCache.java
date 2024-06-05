package com.um.snownote.cache;

public class OntologyCache {

    private CacheLRU<String, Object> cache;


    private OntologyCache() {
        cache = new CacheLRU<>(1000);
    }

    public static OntologyCache getInstance() {
        return new OntologyCache();
    }


    public void addObject(String key, Object value) {
        cache.put(key, value);
    }

    public Object getObject(String key) {
        return cache.get(key);
    }


}
