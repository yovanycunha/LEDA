package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private static final int INITIAL_TOP = -1;
	private static int CAPACITY;	
	private T[] array;
	private int top;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = INITIAL_TOP;
		this.CAPACITY = size ;
		
	}

	@Override
	public T top() {
		if (isEmpty()) {
			return null;
		}
		return this.array[this.top];
	}

	@Override
	public boolean isEmpty() {
		return (this.top == INITIAL_TOP) ? true:false;
	}

	@Override
	public boolean isFull() {
		return (this.fill() == this.CAPACITY) ? true:false;
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (isFull()) {
			throw new StackOverflowException();
		}
		if (!element.equals(null)) {
			this.top++;
			this.array[this.top] = element;
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		}
		T element = this.top();
		this.top--;
		return element;
	}
	
	public int fill() {
		return this.top + 1;
	}

}
