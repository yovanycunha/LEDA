package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	private static final int INVALID_NODE = -1;
	private static final String POST = "post";
	private static final String ORDER = "order";
	private static final String PRE = "pre";
	private static final int ZERO = 0;

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
		return height(this.getRoot());
	}

	protected int height(BSTNode<T> node) {
		if (node.isEmpty()) {
			return INVALID_NODE;
		} else {
			return 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		return searchElem(element, this.getRoot());
	}

	private BSTNode<T> searchElem(T element, BSTNode<T> node) {
		if (element == null) {
			return new BSTNode<T>();
		} else {
			if (node.isEmpty() || element.equals(node.getData()))
				return node;

			else if (element.compareTo(node.getData()) > ZERO)
				return searchElem(element, (BSTNode<T>) node.getRight());

			else
				return searchElem(element, (BSTNode<T>) node.getLeft());

		}

	}

	@Override
	public void insert(T element) {
		insertElem(element, this.getRoot(), null);
	}

	protected BSTNode<T> insertElem(T element, BSTNode<T> node, BSTNode<T> parent) {
		BSTNode<T> aux = new BSTNode<T>();
		if (element != null) {
			if (node.isEmpty()) {
				node.setData(element);
				node.setParent(parent);
				node.setLeft(new BSTNode<>());
				node.setRight(new BSTNode<>());
				node.getLeft().setParent(node);
				node.getRight().setParent(node);
				aux = node;
			} else {
				if (element.compareTo(node.getData()) > ZERO) {
					aux = insertElem(element, (BSTNode<T>) node.getRight(), node);
				} else if (element.compareTo(node.getData()) < ZERO) {
					aux = insertElem(element, (BSTNode<T>) node.getLeft(), node);
				}
			}
		}
		return aux;
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
			return this.maxNode((BSTNode<T>) node.getRight());
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
			return this.minNode((BSTNode<T>) node.getLeft());
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

	private BSTNode<T> sucessorPrivate(BSTNode<T> node) {
		if (this.maximum().equals(node)) {
			return null;
		}

		BSTNode<T> successor = minNode((BSTNode) node.getRight());

		if (successor != null)
			return successor;

		else {
			successor = (BSTNode) node;

			while (successor != null && successor.getData().compareTo(node.getData()) < ZERO)
				;
			successor = (BSTNode) successor.getParent();
		}

		return successor;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty()) {
			return null;
		}
		return predecessorPrivate(node);

	}

	private BSTNode<T> predecessorPrivate(BSTNode<T> node) {
		if (this.minimum().equals(node)) {
			return null;
		}

		BSTNode<T> predeccessor = maxNode((BSTNode<T>) node.getLeft());

		if (predeccessor != null)
			return predeccessor;

		else {
			predeccessor = (BSTNode<T>) node.getParent();

			while (predeccessor != null && node.getData().compareTo(predeccessor.getData()) < ZERO) {
				predeccessor = (BSTNode<T>) predeccessor.getParent();
			}
			return predeccessor;
		}

	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> node = this.search(element);

			if (!node.isEmpty()) {
				remove(node);
			}
		}

	}

	protected BSTNode<T> remove(BSTNode<T> node) {
		if (node.isEmpty() || node.isLeaf()) {
			node.setData(null);
			return node;
		} else if (singleChildrem(node)) {
			return this.removeOneChildrem(node);
		} else {
			return this.removeTwoChildrem(node);
		}

	}

	private BSTNode<T> removeTwoChildrem(BSTNode<T> node) {
		BSTNode<T> auxNode = minNode((BSTNode<T>) node.getRight());
		T auxData = node.getData();

		
		node.setData(auxNode.getData());
		auxNode.setData(auxData);

		return remove(auxNode);
	}

	private BSTNode<T> removeOneChildrem(BSTNode<T> node) {
		BSTNode<T> aux;

		if (leftChildrem(node)) {
			aux = (BSTNode<T>) node.getLeft();
		} else {
			aux = (BSTNode<T>) node.getRight();
		}

		if (node.getParent() == null) {
			aux.setParent(null);
			this.root = aux;
		} else {
			if (isLeftSon(node)) {
				node.getParent().setLeft(aux);
			} else {
				node.getParent().setRight(aux);
			}
			aux.setParent(node.getParent());
		}
		
		return aux;
	}
	
	private boolean isLeftSon(BSTNode<T> node) {
		return (node.getParent() != null &&
				!node.getParent().isEmpty() &&
				!node.getParent().getLeft().isEmpty() &&
				node.getParent().getLeft().getData().equals(node.getData()));
	}

	private boolean singleChildrem(BSTNode<T> node) {
		if (leftChildrem(node) && !rightChildrem(node)) {
			return true;
		} else if (!leftChildrem(node) && rightChildrem(node)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean rightChildrem(BSTNode<T> node) {
		return (node.getLeft().isEmpty() && !node.getRight().isEmpty());
	}

	private boolean leftChildrem(BSTNode<T> node) {
		return (!node.getLeft().isEmpty() && node.getRight().isEmpty());
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

		if (!node.isEmpty()) {
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
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}


}
