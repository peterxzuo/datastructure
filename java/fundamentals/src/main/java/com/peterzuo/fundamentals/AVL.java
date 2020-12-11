package com.peterzuo.fundamentals;

public interface AVL<T extends Comparable> {
    void insert(T object);
    boolean delete(T object);

    int size();
    int height();
}
