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

	private static final int UNBALANCED = 2;
	private static final int UNBALANCE_RIGHT = 1;
	private static final int UNBALANCE_LEFT = -1;
	private static final int ZERO = 0;

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		return (node.isEmpty()) ? ZERO :
			this.height((BSTNode<T>) node.getLeft()) - this.height((BSTNode<T>) node.getRight());
		};

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {

		int balance = calculateBalance(node);

		if (balance > UNBALANCE_LEFT) {
			BSTNode<T> left = (BSTNode<T>) node.getLeft();
			if (calculateBalance(left) <= UNBALANCE_RIGHT) {
				this.leftRotation((BSTNode<T>) node.getLeft());
			}
			this.rightRotation(node);
		} else if (balance < UNBALANCE_RIGHT) {
			BSTNode<T> right = (BSTNode<T>) node.getRight();
			if (calculateBalance(right) >= UNBALANCE_LEFT) {
				this.rightRotation((BSTNode<T>) node.getRight());
			}
			this.leftRotation(node);
		}
	}

	protected void rightRotation(BSTNode<T> node) {

		BSTNode<T> aux = Util.rightRotation(node);
		if (aux.getParent() == null) {
			root = aux;
		}
	}

	protected void leftRotation(BSTNode<T> node) {

		BSTNode<T> aux = Util.leftRotation(node);
		if (aux.getParent() == null) {
			root = aux;
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		int balance = this.calculateBalance(node);
		if (Math.abs(balance) >= UNBALANCED) {
			this.rebalance(node);
		}
		if (node.getParent() != null) {
			rebalanceUp((BSTNode<T>) node.getParent());
		}
	}

	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = this.search(element);
		this.rebalanceUp(node);
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = this.search(element);

		if (!node.isEmpty()) {
			node = super.remove(node);
			this.rebalanceUp(node);
		}
	}
}
