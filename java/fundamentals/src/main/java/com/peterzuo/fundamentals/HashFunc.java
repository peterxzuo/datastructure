package com.peterzuo.fundamentals;

import org.apache.commons.math3.primes.Primes;

public class HashFunc {
    int prime;
    int coeffA;
    int coeffB;

    public int hash(int key){
        return Math.abs((this.coeffA * key + this.coeffB)) % this.prime;
    }

    HashFunc(){
        this.prime = generateRandomPrime();
        this.coeffA = generateRandomCoefficient();
        this.coeffB = generateRandomCoefficient();
    }

    int generateRandomCoefficient(){
        while(true){
            int coeff = (int) Math.floor((double)this.prime * Math.random());
            int prime = Primes.nextPrime(coeff);
            if (prime > 10 && prime < this.prime){
                return prime;
            }
        }
    }

    int generateRandomPrime(){
        while (true) {
            int startInt = (int) Math.floor(Math.random() * (double) Integer.MAX_VALUE);
            int prime = Primes.nextPrime(startInt);
            if (prime > 100000){
                return prime;
            }
        }
    }
}
