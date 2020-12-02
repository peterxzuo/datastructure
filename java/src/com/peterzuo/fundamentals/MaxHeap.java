package com.peterzuo.fundamentals;

import java.util.ArrayList;

public class MaxHeap <T  extends Comparable> implements Heap<T>
{
    ArrayList<T> heap_arr = null;

    public MaxHeap(int initialSize)
    {
        heap_arr = new ArrayList<>(initialSize);
    }

    private void swap(int a, int b)
    {
        if (a==b){
            return;
        }

        T a_obj = heap_arr.get(a);
        heap_arr.set(a, heap_arr.get(b));
        heap_arr.set(b, a_obj);
    }

    private void heapify_topdown(int start)
    {
        while(start * 2 + 1 < heap_arr.size()){
            int left = start * 2 + 1;
            int right = left + 1;
            int max;
            if (right == heap_arr.size() ||  heap_arr.get(left).compareTo(heap_arr.get(right)) > 0){
                max = left;
            }
            else{
                max = right;
            }

            if (heap_arr.get(max).compareTo(heap_arr.get(start)) > 0){
                this.swap(start, max);
                start = max;
            }
            else{
                break;
            }
        }
    }

    private void heapify_bottomup(int start)
    {
        while(start > 0)
        {
            int parent = (start + 1) / 2 - 1;
            if (heap_arr.get(parent).compareTo(heap_arr.get(start)) < 0)
            {
                this.swap(start, parent);
                start = parent;
            } else {
                break;
            }
        }
    }

    @Override
    public void insert(T object)
    {
        heap_arr.add(object);
        heapify_bottomup(heap_arr.size()-1);
    }

    @Override
    public T deleteRoot() {
        if (size()<1){
            return null;
        }

        T top = this.top();
        this.swap(0, size()-1);
        this.heap_arr.remove(size()-1);
        this.heapify_topdown(0);

        return top;
    }

    @Override
    public T top() {
        if (size()<=0){
            return null;
        }

        return heap_arr.get(0);
    }

    @Override
    public int size() {
        return this.heap_arr.size();
    }

    @Override
    public Iterable<T> iterable() {
        return this.heap_arr;
    }

    public static <T extends Comparable> Boolean validateMaxHeap(MaxHeap<T> maxHeap){
        for (int index = 0; index < maxHeap.heap_arr.size() / 2; index++){
            if (maxHeap.heap_arr.get(index).compareTo(maxHeap.heap_arr.get(index * 2)) < 0 || maxHeap.heap_arr.get(index).compareTo(maxHeap.heap_arr.get(index * 2 + 1)) < 0){
                return false;
            }
        }
        return true;
    }
}
