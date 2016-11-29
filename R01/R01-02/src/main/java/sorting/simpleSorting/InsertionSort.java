package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			for (int j = i - 1; j >= leftIndex && array[j].compareTo(array[j + 1]) > 0; j--) {
				Util.swap(array, j, j + 1);
			}
		}
		
		//throw new UnsupportedOperationException("Not Implemented yet!");
	}
}
