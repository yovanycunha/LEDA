package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   private static final int ONE = 1;
   private static final int ZERO = 0;

   public RBTreeImpl() {
      this.root = new RBNode<T>();
   }

   protected int blackHeight() {
      return blackHeightRecursive((RBNode<T>) this.root);

   }

   private int blackHeightRecursive(RBNode<T> node) {
      if (node.isEmpty()) {
         return ONE;
      }

      int height;

      if (isBlackNode(node)) {
         height = ONE;
      } else {
         height = ZERO;
      }

      return height
            + Math.max(blackHeightRecursive((RBNode<T>) node.getLeft()),
                  blackHeightRecursive((RBNode<T>) node.getRight()));

   }

   private boolean isBlackNode(RBNode<T> node) {
      if (node != null) {
         return node.getColour() == Colour.BLACK;
      }

      return true;
   }

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {

      if (isEmpty()) {
         return true;

      } else {
         return verifyChildrenOfRedNodesRecursive((RBNode<T>) this.getRoot());

      }
   }

   private boolean verifyChildrenOfRedNodesRecursive(RBNode<T> node) {
      if (!node.isEmpty()) {

         if (node.getColour().equals(Colour.RED)) {

            if (!isBlackNode((RBNode<T>) node.getLeft()) && !isBlackNode((RBNode<T>) node.getRight())) {
               return false;
            }

         }
         return this.verifyChildrenOfRedNodesRecursive((RBNode<T>) node.getLeft())
               && this.verifyChildrenOfRedNodesRecursive((RBNode<T>) node.getRight());

      }
      return true;
   }

   /**
    * Verifies the black-height property from the root. The method blackHeight
    * returns an exception if the black heights are different.
    */
   private boolean verifyBlackHeight() {
      return verifyBlackHeightRecursive((RBNode<T>) this.root);
   }

   private boolean verifyBlackHeightRecursive(RBNode<T> node) {
	   
	   if (node.isEmpty()) {
		return true;
	
	   
	   } else if (blackHeightRecursive((RBNode<T>)node.getRight()) == blackHeightRecursive((RBNode<T>)node.getLeft())) {
		   return verifyBlackHeightRecursive((RBNode<T>)node.getRight()) &&
				   verifyBlackHeightRecursive((RBNode<T>)node.getLeft());
		   
	   } else {
		   throw new RuntimeException("The black-heights are not equals");
		   
	   }
   }

   @Override
   public void insert(T value) {

      if (value != null) {
         this.insertRecursive(value, (RBNode<T>) this.root);

      }

      RBNode<T> node = insertRecursive(value, (RBNode<T>) this.root);
      fixUpCase1(node);

   }

   private RBNode<T> insertRecursive(T value, RBNode<T> node) {
      RBNode<T> aux = node;
      if (node.isEmpty()) {
         node.setData(value);
         node.setLeft(new RBNode<>());
         node.getLeft().setParent(node);
         node.setRight(new RBNode<>());
         node.getRight().setParent(node);
         node.setColour(Colour.RED);
         fixUpCase1(node);
         ;

      } else if (value.compareTo(node.getData()) < ZERO) {
         this.insertRecursive(value, (RBNode<T>) node.getLeft());
      } else if (value.compareTo(node.getData()) > ZERO) {
         this.insertRecursive(value, (RBNode<T>) node.getRight());
      }
      return aux;
   }

   @Override
   public RBNode<T>[] rbPreOrder() {
      RBNode<T>[] array = new RBNode[size()];
      this.preOrder(array, ZERO, (RBNode<T>) this.getRoot());
      return array;
   }

   private int preOrder(RBNode<T>[] array, int index, RBNode<T> node) {

      if (!node.isEmpty()) {
         array[index++] = node;
         index = preOrder(array, index, (RBNode<T>) node.getLeft());
         index = preOrder(array, index, (RBNode<T>) node.getRight());
      }

      return index;
   }

   // FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {

      if (node == null) {
         return;
      }

      if (node.equals(getRoot())) {
         node.setColour(Colour.BLACK);

      } else {
         fixUpCase2(node);

      }
   }

   protected void fixUpCase2(RBNode<T> node) {

      if (node.getParent() == null) {
         return;
      }

      if (!(((RBNode<T>) node.getParent()).getColour().equals(Colour.BLACK))) {
         fixUpCase3(node);

      }
   }

   protected void fixUpCase3(RBNode<T> node) {

      RBNode<T> uncle = getUncle(node);
      if (uncle.getColour().equals(Colour.RED)) {

         RBNode<T> parent = (RBNode<T>) node.getParent();
         RBNode<T> grandpa = (RBNode<T>) parent.getParent();

         parent.setColour(Colour.BLACK);
         uncle.setColour(Colour.BLACK);
         grandpa.setColour(Colour.RED);
         fixUpCase1(grandpa);

      } else {
         fixUpCase4(node);

      }
   }

   protected void fixUpCase4(RBNode<T> node) {

      RBNode<T> next = node;
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandpa = (RBNode<T>) parent.getParent();

      if (((RBNode<T>) parent.getRight()).equals(node) && ((RBNode<T>) grandpa.getLeft()).equals(parent)) {

         RBNode<T> newNode = (RBNode<T>) Util.leftRotation(parent);

         if (newNode.getParent() == null) {
            this.root = newNode;
         }

         next = (RBNode<T>) node.getLeft();

      } else if (((RBNode<T>) parent.getLeft()).equals(node) && ((RBNode<T>) grandpa.getRight()).equals(parent)) {

         RBNode<T> newNode = (RBNode<T>) Util.rightRotation(parent);

         if (newNode.getParent() == null) {
            this.root = newNode;
         }

         next = (RBNode<T>) node.getRight();
      }
      fixUpCase5(next);
   }

   protected void fixUpCase5(RBNode<T> node) {

      if (node == null) {
         return;
      }

      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandpa = (RBNode<T>) parent.getParent();

      parent.setColour(Colour.BLACK);
      grandpa.setColour(Colour.RED);

      if (((RBNode<T>) parent.getLeft()).equals(node)) {
         RBNode<T> newNode = (RBNode<T>) Util.rightRotation(grandpa);

         if (newNode.getParent() == null) {
            this.root = newNode;
         }

      } else {
         RBNode<T> newNode = (RBNode<T>) Util.leftRotation(grandpa);

         if (newNode.getParent() == null) {
            this.root = newNode;
         }
      }
   }

   private RBNode<T> getUncle(RBNode<T> node) {

      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandpa = (RBNode<T>) parent.getParent();
      RBNode<T> uncle;

      if (((RBNode<T>) grandpa.getLeft()).equals(parent)) {
         uncle = (RBNode<T>) grandpa.getRight();

      } else {
         uncle = (RBNode<T>) grandpa.getLeft();

      }

      return uncle;
   }
}
