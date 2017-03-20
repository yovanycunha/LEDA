package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {

		if (node != null && !node.isEmpty()) {
			
			BTNode<T> parent = node.getParent();
			BTNode<T> right = node.getRight();
			
			right.setParent(parent);
			if (parent != null) {
				
				if (parent.getRight().equals(node)) {
					parent.setRight(right);
				
				} else {
					parent.setLeft(right);
					
				}				
			}
			
			node.setRight(right.getLeft());
			if (node.getRight() != null) {
				node.getRight().setParent(node);
			}
			
			right.setLeft(node);
			node.setParent(right);
			
			return (BSTNode<T>) right;
		}
		return null;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {

		if (node != null && !node.isEmpty()) {
			
			BTNode<T> parent = node.getParent();
			BTNode<T> left = node.getLeft();
			
			left.setParent(parent);
			if (parent != null) {
				
				if (parent.getLeft().equals(node)) {
					parent.setLeft(left);
				
				} else {
					parent.setRight(left);
					
				}				
			}
			
			node.setLeft(left.getRight());
			if (node.getLeft() != null) {
				node.getLeft().setParent(node);
			}
			
			left.setRight(node);
			node.setParent(left);
			
			return (BSTNode<T>) left;
		}
		return null;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
