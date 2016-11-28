package sorting.divideAndConquer.hybridMergesort;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe HybridMergeSort representa a implementação de uma variação do
 * MergeSort que pode fazer uso do InsertionSort (um algoritmo híbrido) da
 * seguinte forma: o MergeSort é aplicado a entradas maiores a um determinado
 * limite. Caso a entrada tenha tamanho menor ou igual ao limite o algoritmo usa
 * o InsertionSort.
 * 
 * A implementação híbrida deve considerar os seguintes detalhes: - Ter
 * contadores das quantidades de MergeSorts e InsertionSorts aplicados, de forma
 * que essa informação possa ser capturada pelo teste. - A cada chamado do
 * método de sort(T[] array) esses contadores são resetados. E a cada chamada
 * interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e
 * INSERTIONSORT_APPLICATIONS são incrementados. - O InsertionSort utilizado no
 * algoritmo híbrido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;

	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;

	public void sort(T[] array, int leftIndex, int rightIndex) {

		if (array != null) {
			if (array.length > SIZE_LIMIT) {
				mergeSort(array, leftIndex, rightIndex);
			} else {
				insertionSort(array, leftIndex, rightIndex);
			}
		}
	}

	private void insertionSort(T[] array, int leftIndex, int rightIndex) {

		if (leftIndex < rightIndex) {
			INSERTIONSORT_APPLICATIONS += 1;
			for (int i = leftIndex; i <= rightIndex; i++) {
				for (int j = i = 1; j > leftIndex && array[j].compareTo(array[j + 1]) > 0; j--) {
					Util.swap(array, j, j + 1);
				}
			}
		}
	}

	private void mergeSort(T[] array, int leftIndex, int rightIndex) {

		if (leftIndex < rightIndex) {
			MERGESORT_APPLICATIONS += 1;
			int med = (rightIndex + leftIndex ) / 2;
			
			mergeSort(array, leftIndex, med);
			mergeSort(array, med + 1, rightIndex);
			
			merge(array, leftIndex, med, rightIndex);
		}
		
	}

	private void merge(T[] array, int leftIndex, int med, int rightIndex) {

		T[] helper = (T[]) new Comparable[rightIndex - leftIndex];
		helper = Arrays.copyOf(array, array.length);
		
		int i = leftIndex;
		int j = med + 1;
		int k = leftIndex;
		
		while(i <= med && j <= rightIndex){
			if (helper[i].compareTo(helper[j]) < 0) {
				array[k] = helper[i];
				i++;
			} else {
				array[k] = helper[j];
				j++;				
			}
			k++;			
		}
		
		while (i <= med) {
			array[k] = helper[i];
			i++;
			k++;
		}
		
		while (j <= rightIndex) {
			array[k] = helper[j];
			j++;
			k++;
		}
	}
}
