package com.peterzuo;

import com.peterzuo.fundamentals.MaxHeap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
	    // write your code here
        testMaxHeap();
    }

    public static void testMaxHeap(){
        MaxHeap<Integer> heap = new MaxHeap<Integer>(1024);

        heap.insert(4);
        heap.insert(5);
        heap.insert(6);
        heap.insert(7);
        heap.insert(8);

        System.out.println(heap.top());
        System.out.println(heap.size());
        heap.iterable().forEach((x) -> System.out.print(x.toString() + ","));

        System.out.println();
        System.out.println(MaxHeap.validateMaxHeap(heap));

        heap.deleteRoot();
        heap.deleteRoot();

        System.out.println(heap.top());
        System.out.println(heap.size());
        heap.iterable().forEach((x) -> System.out.print(x.toString() + ","));

        System.out.println();
        System.out.println(MaxHeap.validateMaxHeap(heap));
    }
}
