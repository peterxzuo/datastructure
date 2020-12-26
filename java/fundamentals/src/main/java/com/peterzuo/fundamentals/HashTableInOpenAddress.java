package com.peterzuo.fundamentals;

public class HashTableInOpenAddress<T extends Comparable> implements HashTable<T>{

    class HashTableEntry{
        enum HashEntryFlag{
            DeleteMe,
            Empty,
            Filled
        }
        
        HashEntryFlag flag;
        T data;
        long key;

        public static long hash(T object, int pos){

        }
    }

    @Override
    public void insert(T object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(T object) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean exists(T object) {
        // TODO Auto-generated method stub
        return false;
    }



}