package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparação não é feita diretamente com os elementos armazenados,
 * mas sim usando um comparator. Dessa forma, dependendo do comparator, a heap
 * pode funcionar como uma max-heap ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	private static final int ZERO = 0;
	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = Util.makeArray(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		int left = this.left(position);
		int right = this.right(position);
		int aux = position;

		if (left <= this.index) {
			aux = left;

			if (right <= this.index) {
				aux = biggest(this.heap, left, right);
			}
		}

		aux = biggest(this.heap, position, aux);

		if (position != aux) {
			Util.swap(this.heap, position, aux);
			this.heapify(aux);
		}

	}

	/**
	 * Dado um array e dois indices esse metodo compara os elementos nos
	 * referentes indices.
	 * 
	 * OBS.: Em caso de igualdade retorna o segundo elemento
	 * 
	 * @param array
	 * @param left
	 * @param right
	 * @return
	 */
	private int biggest(T[] array, int left, int right) {
		return (this.comparator.compare(array[left], array[right]) > ZERO) ? left : right;
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////

		if (element != null) {
			this.heap[++this.index] = element;
			int pos = this.index;

			while (biggest(this.heap, pos, this.parent(pos)) == pos && pos != this.parent(pos)) {
				Util.swap(this.heap, pos, this.parent(pos));
				pos = this.parent(pos);
			}
		}
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = array.length - 1;

		for (int i = this.parent(this.index); i >= ZERO; i--) {
			this.heapify(i);
		}
	}

	@Override
	public T extractRootElement() {

		if (this.isEmpty())
			return null;

		T element = this.rootElement();
		this.remove(ZERO);
		return element;

	}

	private void remove(int index) {

		if (this.index >= ZERO) {
			Util.swap(this.getHeap(), index, this.index--);
			this.heapify(index);
		}
	}

	@Override
	public T rootElement() {
		return (this.isEmpty()) ? null : this.heap[ZERO];
	}

	@Override
	public T[] heapsort(T[] array) {
		// Salvar no comparator atual
		Comparator<T> comparator = this.comparator;

		this.heap = (T[]) new Comparable[INITIAL_SIZE];
		this.index = -1;

		// Novo comparator
		this.comparator = (a, b) -> b.compareTo(a);
		buildHeap(array);

		T[] newArray = (T[]) new Comparable[this.size()];

		for (int i = ZERO; i < newArray.length; i++) {
			newArray[i] = this.extractRootElement();
		}

		// Limpa a heap
		this.heap = (T[]) new Comparable[INITIAL_SIZE];

		// Volta ao comparator original
		this.comparator = comparator;

		return newArray;
	}

	@Override
	public int size() {
		return this.index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
