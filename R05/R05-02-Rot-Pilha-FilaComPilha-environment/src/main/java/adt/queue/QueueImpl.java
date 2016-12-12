package adt.queue;

public class QueueImpl<T> implements Queue<T> {
	
	private static final int ZERO = 0;
	private static final int INITIAL_TAIL = -1;
	private static int CAPACITY;
	private T[] array;
	private int tail;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = INITIAL_TAIL;
		this.CAPACITY = size;
	}

	@Override
	public T head() {
		if (isEmpty()) {
			return null;
		}
		return this.array[ZERO];
	}

	@Override
	public boolean isEmpty() {
		return (this.tail == INITIAL_TAIL) ? true:false;
	}

	@Override
	public boolean isFull() {
		return (this.tail == CAPACITY - 1) ? true:false;
	}

	private void shiftLeft() {
		for (int i = 1; i <= this.tail; i++) {
			this.array[i - 1] = this.array[i];
		}
		this.tail--;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (element.equals(null)) {
			return;
		}
		if (isFull()) {
			throw new QueueOverflowException();
		}
		this.tail++;
		this.array[this.tail] = element;
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		}
		T element = this.head();
		this.shiftLeft();
		return element;
	}

}
