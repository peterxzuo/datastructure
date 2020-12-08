package com.peterzuo.fundamentals;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AVLTree<T extends Comparable>  extends Tree<T> implements AVL<T> {
    int count;

    public AVLTree(){
        super();
        this.count = 0;
    }

    public static class AVLTreeNode<T extends Comparable> extends TreeNode<T>{
        int height;

        public AVLTreeNode(T data) {
            super(data);
            height = 0;
        }
    }

    private void addChild(TreeNode parent, TreeNode child, Boolean asLeftChild){
        if (asLeftChild) {
            parent.left = child;
            child.parent = parent;
        }
        else{
            parent.right = child;
            child.parent = parent;
        }
    }

    protected static int getHeight(TreeNode node){
        if (node == null){
            return -1;
        }

        return ((AVLTreeNode)node).height;
    }

    private void updateHeightBottomUp(AVLTreeNode node){
        if (node == null){
            return;
        }

        do {
            node.height = Math.max(this.getHeight(node.left), this.getHeight(node.right)) + 1;
            node = (AVLTreeNode)node.parent;
        } while (node != null);
    }

    private boolean rightHeavy(AVLTreeNode node){
        return getHeight(node.left) < getHeight(node.right);
    }

    private boolean leftHeavy(AVLTreeNode node){
        return getHeight(node.left) > getHeight(node.right);
    }

    protected static boolean violationBalanceCheck(AVLTreeNode node){
        return (Math.abs(getHeight(node.left) - getHeight(node.right)) > 1);
    }

    private void rotate_left(AVLTreeNode node){
        if (node.right == null){
            return;
        }

        // move node.right to be replace node.
        TreeNode parent = node.parent;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
        } else {
            this.root = node.right;
        }

        // move the node.right's left child branch to be node's right child
        TreeNode right = node.right;
        node.parent = right;
        node.right = node.right.left;
        if (right.left != null) {
            right.left.parent = node;
        }
        right.left = node;
        right.parent = parent;

        updateHeightBottomUp(node);
    }

    private void rotate_right(AVLTreeNode node){
        if (node.left == null){
            return;
        }

        // move node.left to be replace node.
        TreeNode parent = node.parent;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        } else {
            this.root = node.left;
        }

        // move the node.left's right child branch to be node's left child
        TreeNode left = node.left;

        node.parent = left;
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }

        left.right = node;
        left.parent = parent;

        updateHeightBottomUp(node);
    }


    private void balanceAVL(AVLTreeNode node){
        updateHeightBottomUp(node);

        while(node.parent != null){
            AVLTreeNode parent = (AVLTreeNode)node.parent;
            if (violationBalanceCheck(parent)){
                if (rightHeavy(parent) && rightHeavy((AVLTreeNode)parent.right)){
                    rotate_left(parent);
                }
                else
                if (rightHeavy(parent) && leftHeavy((AVLTreeNode)parent.right)){
                    rotate_right((AVLTreeNode) parent.right);
                    rotate_left(parent);
                }
                if (leftHeavy(parent) && leftHeavy((AVLTreeNode)parent.left)){
                    rotate_right(parent);
                }
                else
                if (leftHeavy(parent) && rightHeavy((AVLTreeNode)parent.left)){
                    rotate_left((AVLTreeNode) parent.left);
                    rotate_right(parent);
                }
            }
            node = parent;
        }
    }

    @Override
    public void insert(T object) {
        AVLTreeNode node = new AVLTreeNode(object);

        if (this.root  == null){
            this.root = node;
        } else {
            TreeNode parent = this.root;
            while(parent != null){
                if ( ((Comparable)parent.data).compareTo(object) < 0){
                    if (parent.right != null){
                        parent = parent.right;
                    }
                    else{
                        this.addChild(parent, node, false);
                        balanceAVL(node);
                        break;
                    }
                } else {
                    if (parent.left != null){
                        parent = parent.left;
                    }
                    else{
                        this.addChild(parent, node, true);
                        balanceAVL(node);
                        break;
                    }
                }
            }
        }

        this.count++;
    }

    private void remove_leafnode(TreeNode node){
        if (node.parent != null){
            if (node.parent.left == node){
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
        this.updateHeightBottomUp((AVLTreeNode)node.parent);
        node.parent = null;
        if (this.root == node){
            this.root = null;
        }
        this.count--;
    }

    private void replace_node(TreeNode node, TreeNode replace_node){
        if (node.parent != null){
            if (node.parent.left == node){
                node.parent.left = replace_node;
            } else {
                node.parent.right = replace_node;
            }
            replace_node.parent = node.parent;
        }

        replace_node.left = node.left;
        if (node.left != null){
            node.left.parent = replace_node;
        }

        replace_node.right = node.right;
        if (node.right != null){
            node.right.parent = replace_node;
        }

        if (this.root == node){
            this.root = replace_node;
        }
    }

    @Override
    public boolean delete(T node_data) {
        AtomicReference<AVLTreeNode> found = new AtomicReference<>(null);
        this.traverseRecursive(this.root, TraverseOrder.Inorder, (current)->{
          if (((Comparable)current.data).compareTo(node_data) == 0){
              found.set((AVLTreeNode) current);
          }
        });

        if (found == null){
            return false;
        }

        AVLTreeNode parent;
        AVLTreeNode node = found.get();
        if (node.left == null && node.right == null){
            parent = (AVLTreeNode) node.parent;
            remove_leafnode(node);
        }
        else {

            AVLTreeNode replaceNode = null;
            if (rightHeavy(node)) {
                replaceNode = (AVLTreeNode) node.right.getLeftMostNode();
            } else {
                replaceNode = (AVLTreeNode) node.left.getRightMostNode();
            }

            parent = (AVLTreeNode) replaceNode.parent;
            if (parent == node) {
                parent = replaceNode;
            }

            remove_leafnode(replaceNode);
            replace_node(node, replaceNode);
            updateHeightBottomUp(node);
        }

        balanceAVL(parent);

        return true;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public int height() {
        return getHeight(this.root);
    }

    public static <T extends Comparable> boolean validateBalanced(AVLTree<T> tree){
        AtomicBoolean isBalanced = new AtomicBoolean(true);
        tree.traverseRecursive(tree.root, TraverseOrder.Inorder, (node)->{
            if (AVLTree.violationBalanceCheck((AVLTreeNode)node)){
                isBalanced.set(false);
            }
        });

        return isBalanced.get();
    }
}
