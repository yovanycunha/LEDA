package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {

		if (isFull()) throw new StackOverflowException();
		
		if (element != null) {
			
			this.top.insertFirst(element);
			
		}
	}

	@Override
	public T pop() throws StackUnderflowException {

		if (isEmpty()) throw new StackUnderflowException();
		
		else {
			
			T item = ((RecursiveDoubleLinkedListImpl<T>)this.top).getData();
			this.top.removeFirst();
			
			return item;
		}
	}

	@Override
	public T top() {
		
		if(isEmpty()) return null;
		
		else {
			
			return ((RecursiveDoubleLinkedListImpl<T>)this.top).getData();
			
		}
	}

	@Override
	public boolean isEmpty() {
		
		return (this.top.isEmpty()) ? true:false;
	
	}

	@Override
	public boolean isFull() {

		return (this.top.size() == size) ? true:false;
		
	}

}
