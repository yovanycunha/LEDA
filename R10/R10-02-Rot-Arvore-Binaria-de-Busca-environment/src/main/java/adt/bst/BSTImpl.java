package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void insert(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> maximum() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> minimum() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		return buildWalk("pre");		
	}
	
	@Override
	public T[] order() {
		return buildWalk("order");
	}
	
	@Override
	public T[] postOrder() {
		return buildWalk("pos");
	}

	private T[] buildWalk(String order) {
		
		int sizeOfArray = size();
		T[] array = (T[]) new Comparable[sizeOfArray];
		
		if (!isEmpty()) {
			
			switch (order) {
			case "pre":
				buildPre(array, ZERO, this.getRoot());
				break;

			case "pos":
				buildPos(array, ZERO, this.getRoot());
				break;
			
			case "order":
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
