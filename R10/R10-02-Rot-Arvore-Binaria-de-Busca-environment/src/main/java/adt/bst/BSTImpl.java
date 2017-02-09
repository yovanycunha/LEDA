package adt.bst;

import adt.bt.BTNode;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	private static final String POST = "post";
	private static final String ORDER = "order";
	private static final String PRE = "pre";
	private static  final int ZERO = 0;
	
	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> search(T element) {
		return searchElem(element, this.getRoot());
	}



	private BSTNode<T> searchElem(T element, BSTNode<T> node) {
		if (element == null) {
			return new BSTNode<T>();
		} else {
			if (node.isEmpty() || element.equals(node.getData())) return node;
			
			else if (element.compareTo(node.getData()) > ZERO) return searchElem(element, (BSTNode<T>) node.getRight());
			
			else return searchElem(element, (BSTNode<T>) node.getLeft());
						
		}
	
	}

	@Override
	public void insert(T element) {
		insertElem(element, this.getRoot());
	}

	private void insertElem(T element, BSTNode<T> node) {
		if (element != null) {
			if(node.isEmpty()) {
				node.setData(element);
				node.setLeft(new BTNode<>());
				node.setRight(new BTNode<>());
				node.setParent(node);
			} else {
				if (element.compareTo(node.getData()) > ZERO) {
					insertElem(element, (BSTNode<T>) node.getRight());
				} else if (element.compareTo(node.getData()) < ZERO) {
					insertElem(element, (BSTNode<T>) node.getLeft());
				}
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maxNode(this.getRoot());
	}

	private BSTNode<T> maxNode(BSTNode<T> node) {
		if (node.isEmpty()) {
			return null;
		} else if (node.getRight().isEmpty()) {
			return node;
		} else {
			return maxNode((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		return minNode(this.getRoot());
	}

	private BSTNode<T> minNode(BSTNode<T> node) {
		if (node.isEmpty()) {
			return null;
		} else if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return minNode((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> out = this.search(element);
		
		if (out.isEmpty()) {
			return null;
		}
		return sucessorPrivate(out);
	
	}

	private BSTNode<T> sucessorPrivate(BSTNode<T> out) {
		BSTNode<T> node = this.minNode((BSTNode<T>) out.getRight());
		
		if (node != null) {
			return node;
		} else {
			node = (BSTNode<T>) node.getParent();
			while (node != null && node.getData().compareTo(out.getData()) < ZERO) {
				node = (BSTNode<T>) node.getParent();
			}
			return node;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> out = this.search(element);
		
		if (out.isEmpty()) {
			return null;
		}
		return predecessorPrivate(out);
		
	}

	private BSTNode<T> predecessorPrivate(BSTNode<T> out) {
		BSTNode<T> node = this.maxNode((BSTNode<T>) out.getLeft());
		
		if (node != null) {
			return node;
		} else {
			node = (BSTNode<T>) node.getParent();
			while (node != null && node.getData().compareTo(out.getData()) < ZERO) {
				node = (BSTNode<T>) node.getParent();
			}
			return node;
		}
	
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		return buildWalk(PRE);		
	}
	
	@Override
	public T[] order() {
		return buildWalk(ORDER);
	}
	
	@Override
	public T[] postOrder() {
		return buildWalk(POST);
	}

	private T[] buildWalk(String order) {
		
		int sizeOfArray = size();
		T[] array = (T[]) new Comparable[sizeOfArray];
		
		if (!isEmpty()) {
			
			switch (order) {
			case PRE:
				buildPre(array, ZERO, this.getRoot());
				break;

			case POST:
				buildPos(array, ZERO, this.getRoot());
				break;
			
			case ORDER:
				buildOrder(array, ZERO, this.getRoot());
			default:
				break;
			}
			
		}
		return array;
	
	}

	private int buildOrder(T[] array, int index, BSTNode<T> node) {
		
		if (!node.isEmpty()) {
			index = buildOrder(array, index, (BSTNode<T>) node.getLeft());
			
			array[index++] = node.getData();
			
			index = buildOrder(array, index, (BSTNode<T>) node.getRight());
		}
		return index;
	}

	private int buildPos(T[] array, int index, BSTNode<T> node) {

		if(!node.isEmpty()) {
			index = buildPos(array, index, (BSTNode<T>) node.getLeft());
			index = buildPos(array, index, (BSTNode<T>) node.getRight());
			
			array[index++] = node.getData();
		}
		return index;
	}

	private int buildPre(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			array[index++] = node.getData();
			
			index = buildPre(array, index, (BSTNode<T>) node.getLeft());
			index = buildPre(array, index, (BSTNode<T>) node.getRight());
					
		}
		return index;
	}


	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
