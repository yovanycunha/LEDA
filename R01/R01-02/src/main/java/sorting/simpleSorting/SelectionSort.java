package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The selection sort algorithm chooses the smallest element from the array and
 * puts it in the first position. Then chooses the second smallest element and
 * stores it in the second position, and so on until the array is sorted.
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		int index = 0;
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			index = i;
			
			for (int j = i; j <= rightIndex; j++) {
				if (array[index].compareTo(array[j]) > 0) {
					index = j;
				}
			}
			Util.swap(array, index, i);
		}
		
		//throw new UnsupportedOperationException("Not Implemented yet!");
	}
}
