package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private static int CAPACITY;
	private Stack<T> stackMajor;
	private Stack<T> stackHelper;

	public QueueUsingStack(int size) {
		stackMajor = new StackImpl<T>(size);
		stackHelper = new StackImpl<T>(size);
		this.CAPACITY = size;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (this.isFull()) {
			throw new QueueOverflowException();
		}
		if (element == null) {
			return;
		}
		try {
			this.stackMajor.push(element);
		} catch (StackOverflowException e) {
			// is this real life ? 
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		}
		try {
			this.elemetsTransfer(stackMajor, stackHelper);
			T element = this.stackHelper.pop();
			this.elemetsTransfer(stackHelper, stackMajor);
			return element;
		} catch (StackOverflowException e) {
			throw new RuntimeException();
		} catch (StackUnderflowException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public T head() {
		if (this.isEmpty()) {
			return null;
		}
		try {
			this.elemetsTransfer(stackMajor, stackHelper);
			T element = stackHelper.top();
			this.elemetsTransfer(stackHelper, stackMajor);
			return element;
		} catch (StackOverflowException | StackUnderflowException e) {
			throw new RuntimeException(); // "isso pode, Arnaldo?" - Galvão sobre essa seboseira
		}
	}

	@Override
	public boolean isEmpty() {
		return (this.stackMajor.isEmpty() && this.stackHelper.isEmpty()) ? true:false;
	}

	@Override
	public boolean isFull() {
		return (this.stackMajor.isFull() || this.stackHelper.isFull()) ? true:false;
	}
	
	/**
	 * Transfers elemets from the first stack (stack1) to second stack (stack2) using L.I.F.O.
	 * 
	 * @param stack1
	 * @param stack2
	 * @throws StackUnderflowException 
	 * @throws StackOverflowException 
	 */
	public void elemetsTransfer(Stack stack1, Stack stack2) throws StackOverflowException, StackUnderflowException{
		while (!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
	}

}
