package com.peterzuo.fundamentals;

import java.security.InvalidKeyException;

public interface HashTable<T extends Comparable, V>{
    void insert(T key, V value);
    V delete(T key) throws InvalidKeyException;
    boolean exists(T key);
    V getValue(T key);
}
