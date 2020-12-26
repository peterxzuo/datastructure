package com.peterzuo.fundamentals;

public interface HashTable<T extends Comparable>{
    void insert(T object);
    void delete(T object);
    boolean exists(T object);
}
