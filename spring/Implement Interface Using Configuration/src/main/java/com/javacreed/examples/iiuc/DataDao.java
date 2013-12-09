package com.javacreed.examples.iiuc;

public interface DataDao<T> {

    void save(T data);
    
}
