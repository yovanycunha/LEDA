package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		//verifica sitacoes extraordinarias
		if (array.length == 0 || array == null || leftIndex > rightIndex || leftIndex > 0 || rightIndex > array.length) {
			return;
		}
		
		int maior = encontraMaior(array, leftIndex, rightIndex);
		int[] contagem = new int[maior + 1];
		
		//faz a frequencia dos elementos
		for (int i = leftIndex; i <= rightIndex; i++) {
			contagem[array[i]] += 1;
		}
		
		for (int i = 0, j = leftIndex; i < contagem.length; i++) {
			while (contagem[i] > 0) {
				array[j] = i;
				j++;
				contagem[i]--;
			}
		}
		
	}

	private int encontraMaior(Integer[] array, int leftIndex, int rightIndex) {
		int maior = array[leftIndex];
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			if (maior < array[i]) {
				maior = array[i];
			}
		}
		return maior;
	}
}
