package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

   // TODO Do not forget: you must override the methods insert and remove
   // conveniently.

   private static final int UNBALANCE_LEFT = -1;
private static final int ZERO = 0;

// AUXILIARY
   protected int calculateBalance(BSTNode<T> node) {
      return node == null || node.isEmpty() ? 0 : height((BSTNode<T>) node.getRight())
            - height((BSTNode<T>) node.getLeft());
   }

   // AUXILIARY
   protected void rebalance(BSTNode<T> node) {

      if (node == null || node.isEmpty()) {
         return;
      }

      int balance = calculateBalance(node);

      if (balance < UNBALANCE_LEFT) {
         if (calculateBalance((BSTNode<T>) node.getLeft()) > ZERO) {
            this.leftRotation((BSTNode<T>) node.getLeft());
         }
         this.rightRotation(node);
      } else if (balance > 1) {
         if (calculateBalance((BSTNode<T>) node.getRight()) < ZERO) {
            this.rightRotation((BSTNode<T>) node.getRight());
         }
         this.leftRotation(node);
      }
   }

   protected void rightRotation(BSTNode<T> node) {

      BSTNode<T> aux = Util.rightRotation(node);

      if (node.getParent() == null) {
         root = node;
      }
   }

   protected void leftRotation(BSTNode<T> node) {

      BSTNode<T> aux = Util.leftRotation(node);

      if (node.getParent() == null) {
         root = node;
      }

   }

   // AUXILIARY
   protected void rebalanceUp(BSTNode<T> node) {

      if (node != null) {
         rebalance(node);
         rebalanceUp((BSTNode<T>) node.getParent());
      }
   }

   @Override
   public void insert(T element) {
      insert(element, super.getRoot(), new BSTNode<T>());
   }

   private void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
      if (element == null) {
         return;
      }

      if (node.isEmpty()) {
         node.setData(element);
         node.setLeft(new BSTNode<T>());
         node.setRight(new BSTNode<T>());
         node.setParent(parent);
      } else if (element.compareTo(node.getData()) < ZERO) {
         insert(element, (BSTNode<T>) node.getLeft(), node);
      } else
         insert(element, (BSTNode<T>) node.getRight(), node);
      rebalance(node);
   }
}
