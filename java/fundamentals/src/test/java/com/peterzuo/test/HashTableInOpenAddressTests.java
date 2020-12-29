package com.peterzuo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.InvalidKeyException;

import javax.management.openmbean.KeyAlreadyExistsException;

import com.peterzuo.fundamentals.HashTableInOpenAddress;

import org.junit.jupiter.api.Test;

public class HashTableInOpenAddressTests {

    @Test
    public void insert_test() {
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        hashTable.insert("a", 1);

        assertTrue(hashTable.exists("a"));
        assertFalse(hashTable.exists("b"));
    }

    @Test
    public void insertNull_test() {
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        assertThrows(NullPointerException.class, () -> {
            hashTable.insert(null, 1);
        });
    }

    @Test
    public void insertDuplicateKey_test() {
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        hashTable.insert("a", 1);
        assertThrows(KeyAlreadyExistsException.class, () -> {
            hashTable.insert("a", 1);
        });
    }

    @Test
    public void insertMore_test() {
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        hashTable.insert("a", 1);
        hashTable.insert("b", 1);
        hashTable.insert("c", 1);

        assertTrue(hashTable.exists("a"));
        assertTrue(hashTable.exists("b"));
        assertTrue(hashTable.exists("c"));

        assertFalse(hashTable.exists("d"));
    }

    @Test
    public void delete_test() throws InvalidKeyException {
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        hashTable.insert("a", 1);
        int value = (int)hashTable.delete("a");
        
        assertFalse(hashTable.exists("a"));
        assertEquals(1, value);
    }

    @Test
    public void deleteNull_test(){
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        hashTable.insert("a", 1);
        assertThrows(NullPointerException.class, ()->{
            hashTable.delete(null);
        });
    }

    @Test
    public void deleteNonExistKey_test(){
        HashTableInOpenAddress<String, Integer> hashTable = new HashTableInOpenAddress<String, Integer>(1024);

        assertThrows(InvalidKeyException.class, ()->{
            hashTable.delete("a");
        });
    }

}