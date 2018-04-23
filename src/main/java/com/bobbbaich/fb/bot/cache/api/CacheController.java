package com.bobbbaich.fb.bot.cache.api;

public interface CacheController<T> {
    T find(String topic, String keyWord);

    void close(String topic, String keyWord);

    void add(String topic, String keyWord, T obj);
}