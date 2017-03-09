package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	private static final int ONE = 1;
	private static final int ZERO = 0;

	@Override
	public void insert(T element) {
		BSTNode<T> node = super.insertElem(element, this.root, null);
		this.splay(node);
	}


	@Override
	public void remove(T element) {
		BSTNode<T> aux = super.search(element);
		BSTNode<T> node = super.remove(aux);
		this.splay((BSTNode<T>) node.getParent());
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = search(this.root, element);
		this.splay(node);
		return node;
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			return new BSTNode<T>();
		} else {
			
			if (element.compareTo(node.getData()) == ZERO) {
				return node;
			} else if (element.compareTo(node.getData()) == ONE) {
				if (node.getRight().isEmpty()) {
					return node;
				} else {
					return search((BSTNode<T>) node.getRight(), element);
				}
			} else {
				if (node.getLeft().isEmpty()) {
					return node;
				} else {
					return search((BSTNode<T>) node.getLeft(), element);
				}
			}
		}
	
	}


	private void splay(BSTNode<T> node) {
		if (node != null && node.getParent() != null) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			if (parent.getParent() != null) {
				BSTNode<T> grandParent = (BSTNode<T>) parent.getParent();

				this.doubleRotation(grandParent, parent, node);
			} else {
				this.singleRotation(parent, node);
			}

			if (node.getParent() == null) {
				this.root = node;
			}

			this.splay(node);
		}

	}

	private void singleRotation(BSTNode<T> parent, BSTNode<T> node) {

		if (isLeftChild(node)) {
			Util.rightRotation(parent);
			if (node.getParent() == null)
				this.root = node;
		} else {
			Util.leftRotation(parent);
			if (node.getParent() == null)
				this.root = node;
		}
	}

	private void doubleRotation(BSTNode<T> grandParent, BSTNode<T> parent, BSTNode<T> node) {

		if (isLeftChild(node)) {
			if (isLeftChild(parent)) {
				this.zigZigRotation(grandParent, parent, node);
			} else {
				this.zigZagRotation(grandParent, parent, node);
			}
		} else {
			if (isLeftChild(parent)) {
				this.zagZigRotation(grandParent, parent, node);
			} else {
				this.zagZagRotation(grandParent, parent, node);
			}
		}

	}

	private void zagZagRotation(BSTNode<T> grandParent, BSTNode<T> parent, BSTNode<T> node) {

		Util.leftRotation(grandParent);
		Util.leftRotation(parent);
		if (node.getParent() == null)
			this.root = node;
	}

	private void zagZigRotation(BSTNode<T> grandParent, BSTNode<T> parent, BSTNode<T> node) {

		Util.leftRotation(parent);
		Util.rightRotation(grandParent);
		if (node.getParent() == null)
			this.root = node;
	}

	private void zigZagRotation(BSTNode<T> grandParent, BSTNode<T> parent, BSTNode<T> node) {

		Util.rightRotation(grandParent);
		Util.leftRotation(parent);
		if (node.getParent() == null)
			this.root = node;
	}

	private void zigZigRotation(BSTNode<T> grandParent, BSTNode<T> parent, BSTNode<T> node) {
		Util.rightRotation(grandParent);
		Util.rightRotation(parent);
		if (node.getParent() == null)
			this.root = node;
	}

	private boolean isLeftChild(BSTNode<T> node) {
		return (node.getParent() != null && !node.getParent().isEmpty() && !node.getParent().getLeft().isEmpty()
				&& node.getParent().getLeft().getData().equals(node.getData()));
	}
}
