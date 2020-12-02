package com.peterzuo.fundamentals;

public interface Heap<T extends Comparable> {
    void insert(T object);
    T deleteRoot();
    T top();
    int size();
    Iterable<T> iterable();
}
