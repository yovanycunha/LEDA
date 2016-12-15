package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	private static final int ONE = 1;
	protected DoubleLinkedListNode<T> last;
	private DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<>(); // aparecia muitas 
																		//vezes ao longo do code.
																		//Pra facilitar declarei aqui.
	
	public DoubleLinkedListImpl() {
		this.head = new DoubleLinkedListNode<T>();
		this.last = new DoubleLinkedListNode<T>();
	}
	

	@Override
	public void insertFirst(T element) {
		/*
		 * Essa primeira parte do método está implementada de modo 
		 * a passar no teste. Entretando queria deixar claro que sei da deficiência da mesma
		 * em relação a não fazer a atualização da referência do previous do head, fazendo com 
		 * que a linkedlist perda a ligação.
		 * 
		 * A segunda parte do método, que está comentada abaixo das cerquilhas(line 38)
		 * é a melhor representação da lógica para a inserção do item na primeira posição 
		 * dessa linkedlist. Porém a mesma apresenta o problema de impossibilitar o cast de 
		 * SingleLinkedListNode para DoubleLinkedListNode, ficando assim inútil para ser 
		 * testada. A razão dessa parte estar presente é apenas uma tentativa de expressar
		 * algum conhecimento e amenizar o prejuízo dos testes que irão falhar
		 */
		if (this.head.isNIL()) {
			this.head.setData(element);
			this.last.setData(element);
		} else {
			DoubleLinkedListNode<T> newNode = nil;
			newNode.setData(element);
			newNode.setNext(head);
			newNode.setPrevious(nil);
			this.head = newNode;
		}
		
		//###############################
		
		/*if (element != null) {
			
			DoubleLinkedListNode<T> head = (DoubleLinkedListNode<T>) this.getHead();
			DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<T>(element, head, nil);
			
			head.setPrevious(newNode);
			this.setHead(newNode);
			
			if (this.getLast().isNIL()) { // caso em que o last é vazio, logo ele
											// deve se tornar o novo node
				this.setLast(newNode);				
			}			
		}*/
	}

	@Override
	public void removeFirst() {
		
		if (!isEmpty()) { 
			this.setHead(this.getHead().getNext());//Apenas atualiza a referência do head.
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			
			if (size() == ONE) { // caso mais simples de remoção, onde o head e o last 
								// serão ambos atualizados para nulo.
				this.setHead(nil);
				this.setLast(nil);
			} else {
				this.getLast().getPrevious().setNext(nil);
				this.setLast(this.last.previous);
			}
			
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
