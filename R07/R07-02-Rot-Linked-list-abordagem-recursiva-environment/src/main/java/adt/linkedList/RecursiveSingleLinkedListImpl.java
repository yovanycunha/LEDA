package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	private static final int ZERO = 0;
	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {

		return (this.getData() == null) ? true:false;
		
	}

	@Override
	public int size() {
		int amount = ZERO;
		
		if (this.isEmpty()) return amount;
		
		else {
			
			amount++;
			return amount + this.getNext().size();
						
		}
	}

	@Override
	public T search(T element) {
		
		T wanted = //dead or alive
					null;
		
		if (isEmpty()) return wanted;
		
		if (this.getData().equals(element)) {
			
			return	wanted = element;
			
		} else {
			
			return this.getNext().search(element);
			
		}
	}

	@Override
	public void insert(T element) {

		if(element == null) return;
		
		if (this.isEmpty()) {
			
			this.setData(element);
			this.setNext(new RecursiveSingleLinkedListImpl<T>());
			
		} else {
			
			this.getNext().insert(element);
			
		}
	}

	@Override
	public void remove(T element) {

		if (!isEmpty()) {
			
			if (this.getData().equals(element)) { // Esse método vai sempre remover a primeira
												 //  ocorrência do element

				/*
				 * Ao fazer as mudanças de referência abaixo o método faz com o que 
				 * o node a ser "eliminado" tenha a Data do seu Next e que seu
				 * Next aponte para o Next do próximo.
				 */
				this.setData(this.getNext().getData());
				this.setNext(this.getNext().getNext());
			
			} else {
				
				this.getNext().remove(element);
				
			}
		}
	}

	@Override
	public T[] toArray() {
		
		int sizeOfInfo = this.size();
		T[] info = (T[]) new Object[sizeOfInfo];
		
		recursiveHelper(info, this, ZERO);
	
		return info;
		
	}

	private void recursiveHelper(T[] info, RecursiveSingleLinkedListImpl<T> node, int index) {

		if (!node.isEmpty()) {
			
			info[index] = node.getData();
			index++;
			recursiveHelper(info, node.getNext(), index);
			
		}
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
