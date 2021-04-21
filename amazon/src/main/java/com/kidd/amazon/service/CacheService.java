package com.kidd.amazon.service;

public interface CacheService {
    void setCommonCache(String key, Object value);
    //取
    Object getCommonCache(String key);
}
