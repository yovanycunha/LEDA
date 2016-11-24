package sorting.variationsOfSelectionsort;

import sorting.AbstractSorting;
import util.Util;

/**
 * Este algoritmo eh RECURSIVO e aplica o selectionsort na entrada selectionando o 
 * menor e o maior elemento a cada iteração e colocando eles nas posições corretas. 
 * Nas proximas iterações o espaço de trabalho do algoritmo deve excluir as posiçoes
 * dos elementos das iterações anteriores. 
 */
public class SimultaneousRecursiveSelectionsort<T extends Comparable<T>> extends
		AbstractSorting<T> {
	
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if(leftIndex < rightIndex) {
			
			int indexMenor = leftIndex;
			for (int i = leftIndex + 1; i <= rightIndex ; i++) {
				if (array[i].compareTo(array[indexMenor]) < 0) {
					indexMenor = i;
				}
				/*if (array[i].compareTo(array[indexMaior]) > 0) {
					indexMaior = i;
				}*/				
			}
			
			Util.swap(array, indexMenor, leftIndex);
			leftIndex += 1;
			
			int indexMaior = rightIndex;			
			for (int j = leftIndex; j <= rightIndex; j++) {
				if (array[j].compareTo(array[indexMaior]) > 0) {
					indexMaior = j;					
				}
			}
			
			Util.swap(array, indexMaior, rightIndex);
			rightIndex -= 1;
			
			sort(array, leftIndex , rightIndex);
			
		}
		
		//throw new UnsupportedOperationException("Not implemented yet!");
	}
}
