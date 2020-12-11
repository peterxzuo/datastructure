package com.peterzuo.fundamentals;

import java.util.function.Consumer;

public abstract class Tree<T> {
    public enum TraverseOrder{
        Inorder,
        Left,
        Right
    };

    public static class TreeNode<T> {
        T data;

        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(T data){
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public TreeNode getLeftMostNode(){
            TreeNode node = this;
            while(node.left != null){
                node = node.left;
            }

            return node;
        }

        public TreeNode getRightMostNode(){
            TreeNode node = this;
            while(node.right != null){
                node = node.right;
            }

            return node;
        }

    }

    protected TreeNode root;

    protected void traverseRecursive(TreeNode node, TraverseOrder order, Consumer<TreeNode> consumer){
        if (node == null){
            return;
        }

        switch (order){
            case Inorder:
                consumer.accept(node);
                traverseRecursive(node.left, order, consumer);
                traverseRecursive(node.right, order, consumer);
                break;
            case Left:
                traverseRecursive(node.left, order, consumer);
                consumer.accept(node);
                traverseRecursive(node.right, order, consumer);
                break;
            case Right:
                traverseRecursive(node.right, order, consumer);
                consumer.accept(node);
                traverseRecursive(node.left, order, consumer);
                break;
        }
    }

    public void traverse(TraverseOrder order, Consumer<T> consumer)
    {
        traverseRecursive(this.root, order,
                (node)-> {
                    consumer.accept((T) node.data);
                });
    }
}
