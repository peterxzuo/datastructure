package com.peterzuo.fundamentals;

import java.security.InvalidKeyException;

import javax.management.openmbean.KeyAlreadyExistsException;

public class HashTableInOpenAddress<T extends Comparable, V> implements HashTable<T, V>{
    enum HashEntryFlag{
        DeleteMe,
        Empty,
        Filled
    }

    static class HashTableEntry{
        
        public HashEntryFlag flag;
        public Object value;
        public Object key;

        HashTableEntry(){
            this.flag = HashEntryFlag.Empty;
        }
    }

    static int MINIMAL_HASHTABLE_SIZE = 8;
    static int MAX_COLLISION_RETRY = 7;

    int maxSize;
    int tableSize;
    HashTableEntry[] array;

    HashFunc hash1;
    HashFunc hash2;

    public HashTableInOpenAddress(int initMaxSize){
        this.maxSize = Math.max(initMaxSize, MINIMAL_HASHTABLE_SIZE);
        this.tableSize = 0;
        this.hash1 = new HashFunc();
        this.hash2 = new HashFunc();
        this.array = new HashTableEntry[(int)this.maxSize];
    }

    private int hash(T data, int pos, int maxSize){
        int hashCode = data.hashCode();
        return (Math.abs(hash1.hash(hashCode) + pos * (hash2.hash(hashCode)))) % maxSize;
    }

    private void rehashTable(int desiredSize){
        HashTableEntry [] existingArray = this.array;
        
        this.array = new HashTableEntry[desiredSize];
        this.maxSize = desiredSize;
        this.tableSize = 0;

        for (int i=0; i<existingArray.length; i++){
            if (existingArray[i] != null && existingArray[i].flag == HashEntryFlag.Filled){
                this.insert((T)existingArray[i].key, (V)existingArray[i].value);                
            }
        }
    }

    @Override
    public void insert(T key, V value) {
        if (key == null)
            throw new NullPointerException();

        int pos = 1;
        while(true){
            int hash = hash(key, pos, this.maxSize);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.DeleteMe || this.array[hash].flag == HashEntryFlag.Empty){
                if (this.array[hash] == null){
                    this.array[hash] = new HashTableEntry();
                }         

                this.array[hash].value = value;
                this.array[hash].flag = HashEntryFlag.Filled;
                this.array[hash].key = key;
                break;
            } else {
                if (key.compareTo(this.array[hash].key)==0){
                    throw new KeyAlreadyExistsException();
                }
            }
            pos++; 

            // COLLISION TOO MUCH make indicate clusters - rehashing tables
            if (pos > MAX_COLLISION_RETRY){
                this.hash1 = new HashFunc();
                this.hash2 = new HashFunc();
                rehashTable(this.maxSize);
                insert(key, value);
                return;
            }
        }
        
        this.tableSize++;
        if (this.tableSize > this.maxSize / 2){
            rehashTable(this.maxSize * 2);
        }
    }

    @Override
    public V delete(T key) throws InvalidKeyException {
        if (key == null)
            throw new NullPointerException();

        int pos = 1;
        while(true){
            int hash = hash(key, pos, this.maxSize);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.Empty){
                throw new InvalidKeyException("Key doesn't exist"); 
            }

            if (this.array[hash].flag == HashEntryFlag.Filled && key.compareTo(this.array[hash].key)==0){
                V deletedValue = (V)this.array[hash].value;

                this.array[hash].flag = HashEntryFlag.DeleteMe;
                this.tableSize--;

                if (this.tableSize < this.maxSize / 4 && this.maxSize > MINIMAL_HASHTABLE_SIZE){
                    this.rehashTable(this.maxSize / 2);
                }

                return deletedValue;
            }

            pos++;
        }
    }

    @Override
    public boolean exists(T key) {
        int pos = 1;
        while(true){
            int hash = hash(key, pos, this.maxSize);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.Empty){
                return false;
            }

            if (this.array[hash].flag == HashEntryFlag.Filled && key.compareTo(this.array[hash].key)==0){
                return true;
            }
            
            pos++;
        }
    }

    @Override
    public V getValue(T key) {
        int pos = 1;
        while(true){
            int hash = hash(key, pos, this.maxSize);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.Empty){
                throw new RuntimeException("Key doesn't exist"); 
            }

            if (this.array[hash].flag == HashEntryFlag.Filled && key.compareTo(this.array[hash].key)==0){
                return (V)this.array[hash].value;
            }

            pos++;
        }
      }
}