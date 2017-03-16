package adt.btree;

import java.util.ArrayList;
import java.util.List;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	private static final int TWO = 2;
	private static final int ONE = 1;
	private static final int ZERO = 0;
	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}
	
	public void setRoot(BNode<T> node) {
		this.root = node;
	}
	
	public int getOrder() {
		return this.order;
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {

		if (node.isEmpty()) {
			return -ONE;
		
		} else {
		
			if (node.getChildren().size() > ZERO) {
				return height(node.getChildren().getFirst()) + ONE;
			
			} else {
				
				return ZERO;
			}
		}		
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		
		ArrayList<BNode<T>> list = new ArrayList<>();
		BNode<T>[] array;
		
		if (isEmpty()) {
			array = new BNode[list.size()];
			return array;
			
		} else {
			
			depthLeftOrder(getRoot(), list);
			array = new BNode[list.size()];
			for (int i = ZERO; i < list.size(); i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
	}	

	private void depthLeftOrder(BNode<T> node, ArrayList<BNode<T>> list) {

		if (node.isEmpty()) {
			return;
			
		} else {
			
			list.add(node);
			List<BNode<T>> children = node.getChildren();
			
			for (BNode<T> child : children) {
				depthLeftOrder(child, list);
			}
		}
		
	}

	@Override
	public int size() {
		
		return size(this.root);
	
	}

	private int size(BNode<T> node) {
		
		if (isEmpty()) {
			return ZERO;
		
		} else {
		
			int total = node.size();
			List<BNode<T>> children = node.getChildren();
			
			for (BNode<T> child : children) {
				total += size(child);
			}		
			return total;
		}
	}

	@Override
	public BNodePosition<T> search(T element) {
		
		if (element == null) {
			return new BNodePosition<>();
		}
		
		return search(this.root, element);
	}

	private BNodePosition<T> search(BNode<T> node, T element) {
		
		int index = ZERO;
		
		while (index < node.size() 
				&& element.compareTo(node.getElementAt(index)) > ZERO) {
			
			index++;
		}
		
		if (index < node.size() && element.equals(node.getElementAt(index))) {
			return new BNodePosition<>(node, index);
		
		} else if (node.isLeaf()) {
			return new BNodePosition<>();
		
		} else {
			return search(node.getChildren().get(index), element);
			
		}
	}

	@Override
	public void insert(T element) {

		if (element == null) {
			return;
		}
		
		insert(this.root, element);
		
	}

	private void insert(BNode<T> node, T element) {
		
		int index = ZERO;
		
		while (index < node.size() 
				&& node.getElementAt(index).compareTo(element) < ZERO) {
			index++;
		}
		
		if (index >= node.size() 
		|| !node.getElementAt(index).equals(element)) {
			
			if (node.isLeaf()) {				
				node.addElement(element);
			
			} else {
				insert(node.getChildren().get(index), element);
			}
		}
		
		if (node.getMaxKeys() < node.size()) {
			split(node);
		}
		
	}

	private void split(BNode<T> node) {
		
		int indexMiddle = node.getElements().size() / TWO;
		
		BNode<T> leftNode = new BNode<>(getOrder());
		BNode<T> rightNode = new BNode<>(getOrder());
		
		for (int i = ZERO; i < node.size(); i++) {
			
			if (i < indexMiddle) {
				leftNode.getElements().add(node.getElementAt(i));
				
			} else if (i > indexMiddle) {
				rightNode.getElements().add(node.getElementAt(i));
			}
			
		}
		
		T mid = node.getElementAt(indexMiddle);
		
		if (!node.isLeaf()) {
			
			List<BNode<T>> children = node.getChildren();
			
			if (children.size() > ZERO) {
				
				int indexChildren = ZERO;
				
				for (int i = ZERO; i < children.size(); i++) {
					
					
					if (i <= indexMiddle) {
						leftNode.addChild(i, children.get(i));
					
					} else {
						rightNode.addChild(indexChildren++, children.get(i));
						
					}
				}
			}
		}
		
		if (node.equals(getRoot())) {
			BNode<T> newRoot = new BNode<>(getOrder());
			newRoot.addElement(mid);
			node.setParent(newRoot);
			setRoot(newRoot);
			
			newRoot.addChild(ZERO, leftNode);
			newRoot.addChild(ONE, rightNode);
			newRoot.getChildren().get(ZERO).setParent(newRoot);
			newRoot.getChildren().get(ONE).setParent(newRoot);			
			
		} else {
			node.addChild(ZERO, leftNode);
			node.addChild(ONE, rightNode);
			
			promote(node);			
		}	
	}

	private void promote(BNode<T> node) {
		
		int indexMiddle = node.getElements().size() / TWO;
		T mid =node.getElementAt(indexMiddle);
		
		node.getElements().clear();
		node.addElement(mid);
		
		BNode<T> parent = node.getParent();
		
		if (parent != null) {
			
			node.getChildren().get(ZERO).setParent(parent);
			node.getChildren().get(ONE).setParent(parent);
			
			int index = parent.getChildren().indexOf(node);
			
			parent.addElement(mid);
			parent.addChild(index, node.getChildren().get(ZERO));
			parent.addChild(index + ONE, node.getChildren().get(ONE));
			
			node.getChildren().get(ZERO).setParent(parent);
			node.getChildren().get(ONE).setParent(parent);
			
			parent.getChildren().remove(node);
		}
	
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
