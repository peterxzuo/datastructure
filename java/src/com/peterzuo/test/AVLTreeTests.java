package com.peterzuo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.peterzuo.fundamentals.AVLTree;
import com.peterzuo.fundamentals.Tree;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class AVLTreeTests {

    @Test
    public void insert_RightRotation() {
        AVLTree<Integer> tree = new AVLTree<Integer>();

        for (int i=7; i>0; i--) {
            tree.insert(i);
        }

        AtomicInteger visitOrder = new AtomicInteger(1);
        tree.traverse(Tree.TraverseOrder.Left, (data)->{
            assertEquals(visitOrder.get(), data);
            visitOrder.getAndIncrement();
        });

        assertEquals(7, tree.size());
        assertEquals(2, tree.height());
    }

    @Test
    public void insert_LeftRotation() {
        AVLTree<Integer> tree = new AVLTree<Integer>();

        for (int i=1; i<=7; i++) {
            tree.insert(i);
        }

        AtomicInteger visitOrder = new AtomicInteger(1);
        tree.traverse(Tree.TraverseOrder.Left, (data)->{
            assertEquals(visitOrder.get(), data);
            visitOrder.getAndIncrement();
        });

        assertEquals(7, tree.size());
        assertEquals(2, tree.height());
    }

    @Test
    public void delete_LeftNode() {

        AVLTree<Integer> tree = new AVLTree<Integer>();

        for (int i=1; i<=7; i++) {
            tree.insert(i);
        }

        tree.delete(2);
        AtomicInteger visitOrder = new AtomicInteger(0);
        int[] expectedOrder = {1, 3, 4, 5, 6, 7, 8};
        tree.traverse(Tree.TraverseOrder.Left, (data)->{
            assertEquals(expectedOrder[visitOrder.get()], data);
            visitOrder.getAndIncrement();
        });

        assertEquals(6, tree.size());
        assertEquals(2, tree.height());
    }

    @Test
    public void delete_BranchNode() {

        AVLTree<Integer> tree = new AVLTree<Integer>();

        for (int i=1; i<=8; i++) {
            tree.insert(i);
        }
        assertEquals(3, tree.height());

        tree.delete(2);
        assertEquals(3, tree.height());

        tree.delete(3);
        assertEquals(2, tree.height());

        AtomicInteger visitOrder = new AtomicInteger(0);
        int[] expectedOrder = {1, 4, 5, 6, 7, 8};
        tree.traverse(Tree.TraverseOrder.Left, (data)->{
            assertEquals(expectedOrder[visitOrder.get()], data);
            visitOrder.getAndIncrement();
        });

        assertEquals(6, tree.size());
        assertEquals(2, tree.height());
    }


    @Test
    public void delete_RootNode() {

        AVLTree<Integer> tree = new AVLTree<Integer>();

        for (int i=1; i<=8; i++) {
            tree.insert(i);
        }

        tree.delete(4);
        tree.delete(3);
        tree.delete(2);
        AtomicInteger visitOrder = new AtomicInteger(0);
        int[] expectedOrder = {1, 5, 6, 7, 8};
        tree.traverse(Tree.TraverseOrder.Left, (data)->{
            assertEquals(expectedOrder[visitOrder.get()], data);
            visitOrder.getAndIncrement();
        });

        assertEquals(5, tree.size());
        assertEquals(2, tree.height());
    }
}
