package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	private static final int ZERO = 0;

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return (this.getHead().isNIL()) ? true : false;
	}

	@Override
	public int size() {
		int size = ZERO;
		SingleLinkedListNode<T> helper;
		helper = this.getHead();

		while (!helper.isNIL()) {
			helper = helper.getNext();
			size++;
		}

		return size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> helper = this.getHead();
		T wanted //dead or alive
				= null;

		if (element != null) {
			if (!isEmpty()) {
				while (!helper.isNIL()) {
					if (helper.getData().equals(element)) {
						return helper.getData();
					}
					helper = helper.getNext();
				}
			}
		}

		return wanted;
	}

	@Override
	public void insert(T element) {
		if (element.equals(null)) {
			return;
		}
		
		SingleLinkedListNode<T> next = new SingleLinkedListNode<>();
		
		if (isEmpty()) { // caso em que o inserção será feita na head pois a lista está vazia
			
			SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, next);
			this.setHead(newNode);
			
		} else {
			SingleLinkedListNode<T> helper = this.getHead();
			
			while (!helper.isNIL()) helper = helper.getNext();
			
			helper.setData(element);
			helper.setNext(next);
		}
	}

	@Override
	public void remove(T element) {
		SingleLinkedListNode<T> helper = this.getHead();
		SingleLinkedListNode<T> previous = null;
		
		if (this.getHead().getData().equals(element)) { //caso em que a remoção será feita na head
			this.setHead(helper.getNext());
		} else {
			
			while (!helper.isNIL() && !helper.getData().equals(element)) { // esse laço coloca o helper
																		   // no node vai ser eliminado
				previous = helper;
				helper = helper.getNext();
			}
			
			if (!helper.isNIL()) { // o helper possivelmente pode ir até o fim da LinkedList 
									//sem encontrar o elemento
									// desejado
				
				previous.setNext(helper.getNext());
			}
		}
	}

	@Override
	public T[] toArray() {
		int sizeOfArray = this.size();
		int index = ZERO;
		
		T[] array = (T[]) new Object[sizeOfArray];
		SingleLinkedListNode<T> helper = this.getHead();
		
		while (!helper.isNIL()) {
			array[index] = helper.getData();
			index++;
			helper = helper.getNext();
		}
		
		return array;
	
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
