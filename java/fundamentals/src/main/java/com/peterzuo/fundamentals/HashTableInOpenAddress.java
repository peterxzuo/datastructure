package com.peterzuo.fundamentals;

import java.security.InvalidKeyException;
import java.security.KeyException;

import javax.management.openmbean.KeyAlreadyExistsException;

public class HashTableInOpenAddress<T extends Comparable> implements HashTable<T>{
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

    int maxSize;
    int tableSize;
    HashTableEntry[] array;

    HashFunc hash1;
    HashFunc hash2;

    public HashTableInOpenAddress(int initMaxSize){
        this.maxSize = initMaxSize;
        this.tableSize = 0;
        this.hash1 = new HashFunc();
        this.hash2 = new HashFunc();
        this.array = new HashTableEntry[(int)this.maxSize];
    }

    private int hash(T data, int pos){
        int hashCode = data.hashCode();
        return (hash1.hash(hashCode) % maxSize + pos * (hash2.hash(hashCode) % maxSize)) % maxSize;
    }

    @Override
    public void insert(T key, Object value) {
        if (key == null)
            throw new NullPointerException();

        int pos = 1;
        while(true){
            int hash = hash(key, pos);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.DeleteMe || this.array[hash].flag == HashEntryFlag.Empty){
                if (this.array[hash] == null){
                    this.array[hash] = new HashTableEntry();
                }

                this.array[hash].value = value;
                this.array[hash].flag = HashEntryFlag.Filled;
                this.array[hash].key = key;
                break;
            } else {
                if (this.array[hash].key == key){
                    throw new KeyAlreadyExistsException();
                }
            }
            pos++; 
        }
        
        this.tableSize++;
    }

    @Override
    public Object delete(T key) throws InvalidKeyException {
        if (key == null)
            throw new NullPointerException();

        int pos = 1;
        while(true){
            int hash = hash(key, pos);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.Empty){
                throw new InvalidKeyException("Key doesn't exist"); 
            }

            if (this.array[hash].flag == HashEntryFlag.Filled && this.array[hash].key == key){
                this.array[hash].flag = HashEntryFlag.DeleteMe;
                this.tableSize--;

                return this.array[hash].value;
            }

            pos++;
        }        
    }

    @Override
    public boolean exists(T key) {
        int pos = 1;
        while(true){
            int hash = hash(key, pos);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.Empty){
                return false;
            }

            if (this.array[hash].flag == HashEntryFlag.Filled && this.array[hash].key == key){
                return true;
            }
            
            pos++;
        }
    }

    @Override
    public Object getValue(T key) {
        int pos = 1;
        while(true){
            int hash = hash(key, pos);
            if (this.array[hash] == null || this.array[hash].flag == HashEntryFlag.Empty){
                throw new RuntimeException("Key doesn't exist"); 
            }

            if (this.array[hash].flag == HashEntryFlag.Filled && this.array[hash].key == key){
                return this.array[hash].value;
            }

            pos++;
        }
      }
}