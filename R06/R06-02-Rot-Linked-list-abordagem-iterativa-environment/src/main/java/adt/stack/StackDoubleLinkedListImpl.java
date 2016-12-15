package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {

		if(element == null) return;
		
		if(isFull()) throw new StackOverflowException();
		
		else {
			this.top.insert(element);
		}
		
	}

	@Override
	public T pop() throws StackUnderflowException {
		
		if (isEmpty()) throw new StackUnderflowException();
		
		else {
			T elem = this.top();
			this.top.removeLast();
			return elem;
			/*
			 * Esse método vai falhar nos testes devido a dependência do
			 * método que está implementado na DoubleLinkedList.
			 * Lá estão mais explicações.
			 * 
			 */
		}
	}

	@Override
	public T top() {
		if (isEmpty()) return null;
		else {
			T elem = ((DoubleLinkedListImpl<T>) top).getLast().getData();
			return elem;
		}
	}

	@Override
	public boolean isEmpty() {
		return (this.top.isEmpty()) ? true:false;
	}

	@Override
	public boolean isFull() {
		return (this.top.size() == this.size) ? true:false;
	}

}
