package com.peterzuo.fundamentals;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.codec.digest.MurmurHash2;
import org.apache.commons.math3.primes.Primes;

/* 
  Hash function uses Murmur2 for speed.
  Based on https://softwareengineering.stackexchange.com/questions/49550/which-hashing-algorithm-is-best-for-uniqueness-and-speed
 */

public class ConsistentHashMap<T> {
    ArrayList<T> nodes;
    TreeMap<Integer, T> hashRing;
    int hashSeed;

    ConsistentHashMap(Collection<T> initNodes){
        this.nodes = new ArrayList(initNodes);
        Random random = new Random();
        this.hashSeed = Primes.nextPrime(random.nextInt());

    }

    private void initializeHashRing(){
        hashRing = new TreeMap<Integer, T>();
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        int step = (max - min) / nodes.size();
        for (int i=0; i<nodes.size(); i++){
            hashRing.put(i * step, nodes.get(i));
        }
    }

    private int hashKey(String key){
        byte[] bytes = key.getBytes(Charset.forName("UTF-8"));
        return MurmurHash2.hash32(bytes, bytes.length, hashSeed);
    }

    public T select(String key){
        int hash = hashKey(key);
        return hashRing.floorEntry(hash).getValue();
    }

    public void addNode(T node){
        // Rebalance existing nodes to the new node.
        // Strategy: It achieves: 
        // 1. rebalance the range for each node evenly - all nodes are equal weighted. 
        // 2. It minimize the move of the keys between nodes. 

        HashMap<T, ArrayList<HashKeyRange>> nodeRanges = new HashMap<>();
        this.hashRing.forEach((hashKey, server)->{
            if (nodeRanges.containsKey(server)){
                //update value in the hashmap.
            }
        });

    }

    public void removeNode(T node){

    }

    class HashKeyRange{
        int floorKey;
        int ceilingKey;
        HashKeyRange(int floor, int ceiling){
            this.floorKey = floor;
            this.ceilingKey = ceiling;
        }
    } 
}