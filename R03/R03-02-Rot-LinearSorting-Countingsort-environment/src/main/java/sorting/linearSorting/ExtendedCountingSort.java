package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		//verifica sitacoes extraordinarias
		if (array.length == 0 || array == null || leftIndex > rightIndex || leftIndex > 0
				|| rightIndex > array.length) {
			return;
		}

		int maior = encontraMaior(array, leftIndex, rightIndex);
		int menor = encontraMenor(array, leftIndex, rightIndex);
		int[] contagem = new int[(maior - menor) + 1];
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			contagem[array[i] - menor]++; 
		}
		
		for (int i = 0, j = leftIndex; i < contagem.length; i++) {
			while (contagem[i] > 0) {
				array[j] = i + menor;
				j++;
				contagem[i]--;
			}
		}

	}

	private int encontraMenor(Integer[] array, int leftIndex, int rightIndex) {
		int menor = array[leftIndex];
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			if (menor > array[i]) {
				menor = array[i];
			}
		}
		return menor;
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
