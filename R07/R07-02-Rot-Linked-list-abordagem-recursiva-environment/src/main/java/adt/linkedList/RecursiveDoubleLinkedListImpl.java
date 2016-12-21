package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends
		RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}
	
	@Override
	/*
	 * A sobrescrita desse método se dá pelo fato de que é necessário, em alguns casos, 
	 * atualizar uma referência, o que não seria possível caso fosse usado o método 
	 * implementado no super
	 */
	public void insert(T element) {

		if(element == null) return;
		
		if (this.isEmpty()) {
			
			this.setData(element);
			this.setNext(new RecursiveDoubleLinkedListImpl<T>());
			
		} else {
			
			this.getNext().insert(element);
			
		}
	}
	
	
	@Override
	public void insertFirst(T element) {

		/* Node auxiliar que vai receber as seguintes referências:
		*  O próximo do auxiliar recebe o próximo do this  (helper.next = this.next)
		*  O anterior do auxilar apontará para this        (helper.previous = this)
		*  E a Data do helper será a Data do this          (helper.data = this.data)
		*  Dessa forma "andamos" com o item que estava no head e não mudamos a referência do head
		*  Essa mesma referência vai ser atualizada para o valor (element)
		*  a ser inserido no início   
		*/		
		RecursiveDoubleLinkedListImpl<T> helper = new RecursiveDoubleLinkedListImpl<>();
		helper.setData(this.getData());
		helper.setNext(this.getNext());
		helper.setPrevious(this);
		
		this.setData(element);
		this.setNext(helper);
	}

	@Override
	public void removeFirst() {

		if (!isEmpty()) {
			
			//Node auxiliar que é uma cópia do next do elemento a ser removido
			RecursiveDoubleLinkedListImpl<T> helper = (RecursiveDoubleLinkedListImpl<T>) this.getNext();
			
			//O Data do this é a data do next (helper.data)
			//O next do this é o next do next (helper.next)
			this.setData(helper.getData());
			this.setNext(helper.getNext());
			
			if (!helper.getNext().isEmpty()) { // Caso haja um next do helper (helper.next != null)
											   // o previous desse next deve apontar para this
											   // (helper.next.previous = this)
				
				RecursiveDoubleLinkedListImpl<T> aux = (RecursiveDoubleLinkedListImpl<T>) helper.getNext();
				aux.setPrevious(this);
				
			}
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			
			if (this.getNext().isEmpty()) {
				
				this.setData(null);
				this.setPrevious(null);
				
			} else {
				
				((RecursiveDoubleLinkedListImpl<T>)this.getNext()).removeLast();
				
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
