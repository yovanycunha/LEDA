package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em
 * suas funcionalidades e possui um metodo de ordenar um array dado como
 * parametro, retornando o resultado do percurso desejado que produz o array
 * ordenado.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private static final int ZERO = 0;
	private Comparator<T> comparator;

	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		clearTree(this.getRoot());
		
		for (int i = 0; i < array.length; i++) {
			this.insert(array[i]);
		}
		
		return array = this.order();
	}

	private void clearTree(BSTNode<T> node) {
		node.setData(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(null);
	}

	@Override
	public T[] reverseOrder() {
		
		int size = this.size();
		T[] array = (T[]) new Comparable[size];
		
		reverse(array, ZERO, this.getRoot());
		
		return array;
	}
		
	private int reverse(T[] array, int index, BSTNode<T> node){
		
		if (!node.isEmpty()) {
			index = reverse(array, index, (BSTNode<T>) node.getRight());
			array[index++] = node.getData();
			index = reverse(array, index, (BSTNode<T>) node.getLeft());
		}
		
		return index;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public BSTNode<T> search(T element) {
		return searchElem(element, this.getRoot());
	}

	private BSTNode<T> searchElem(T element, BSTNode<T> node) {
		if (element == null) {
			return new BSTNode<T>();
		} else {
			if (node.isEmpty() || element.equals(node.getData())) {
				return node;
			} else if (comparator.compare(element, node.getData()) > ZERO) {
				return searchElem(element, (BSTNode<T>) node.getRight());
			} else /* if (comparator.compare(element, node.getData()) < ZERO) */ {
				return searchElem(element, (BSTNode<T>) node.getLeft());
			}
		}
	}

	@Override
	public void insert(T element) {
		insertElem(element, this.getRoot());
	}

	private void insertElem(T element, BSTNode<T> node) {
		if (element != null) {
			if (node.isEmpty()) {
				node.setData(element);
				node.setLeft(new BSTNode<>());
				node.setRight(new BSTNode<>());
				node.getLeft().setParent(node);
				node.getRight().setParent(node);
			} else {
				if (comparator.compare(element, node.getData()) > ZERO) {
					insertElem(element, (BSTNode<T>) node.getRight());
				} else /*
						 * if (comparator.compare(element, node.getData()) <
						 * ZERO)
						 */ {
					insertElem(element, (BSTNode<T>) node.getLeft());
				}
			}
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> out = this.search(element);

		if (out.isEmpty()) {
			return null;
		}
		return sucessor(out);
	}

	private BSTNode<T> sucessor(BSTNode<T> node) {

		if (this.maximum().equals(node)) {
			return null;
		}

		BSTNode<T> successor = this.minNode((BSTNode) node.getRight());

		if (successor != null) {
			return successor;
		} else {
			successor = (BSTNode) node.getParent();

			while (successor != null && comparator.compare(successor.getData(), node.getData()) < ZERO)
				successor = (BSTNode) successor.getParent();			
		}
		
		return successor;
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

			while (predeccessor != null && comparator.compare(node.getData(), predeccessor.getData()) < ZERO) {
				predeccessor = (BSTNode<T>) predeccessor.getParent();
			}
			return predeccessor;
		}
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
}
