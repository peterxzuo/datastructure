package com.peterzuo.fundamentals;

import java.security.InvalidKeyException;

public interface HashTable<T extends Comparable>{
    void insert(T key, Object value);
    Object delete(T key) throws InvalidKeyException;
    boolean exists(T key);
    Object getValue(T key);
}
