package orderStatistic;

import util.Util;

/**
 * Uma implementacao da interface KLargest que usa estatisticas de ordem para
 * retornar um array com os k maiores elementos de um conjunto de dados/array.
 * 
 * A k-esima estatistica de ordem de um conjunto de dados eh o k-esimo menor
 * elemento desse conjunto. Por exemplo, considere o array [4,8,6,9,12,1]. A 3a
 * estatistica de ordem eh 6, a 6a estatistica de ordem eh 12.
 * 
 * Note que, para selecionar os k maiores elementos, pode-se pegar todas as
 * estatisticas de ordem maiores que k.
 * 
 * Requisitos do algoritmo: - DEVE ser in-place. Voce pode modificar o array
 * original - Voce DEVE usar alguma ideia de algoritmo de ordenacao visto em
 * sala para calcular estatisticas de ordem.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class KLargestOrderStatisticsImpl<T extends Comparable<T>> implements KLargest<T> {

   @Override
   public T[] getKLargest(T[] array, int k) {

      T estat = orderStatistics(array, k);
      Integer[] maiores = new Integer[array.length - k];
      for (int i = k; i < array.length; i++) {
         maiores[i] = (Integer) array[i];
      }
      return (T[]) maiores;
      // este metodo deve obrigatoriamente usar o orderStatistics abaixo.
   }

   /**
    * Metodo que retorna a k-esima estatistica de ordem de um array, usando a
    * ideia de algum algoritmo de ordenacao visto em sala. Caso nao exista a
    * k-esima estatistica de ordem, entao retorna null.
    * 
    * Obs: o valor de k deve ser entre 1 e N.
    * 
    * @param array
    * @param k
    * @return
    */
   public T orderStatistics(T[] array, int k) {
      if (k > array.length || k < 0 || array.length == 0 || array == null) {
         return null;
      }

      quickSort(array, 0, array.length - 1);

      return array[k - 1];
   }

   private void quickSort(T[] array, int ini, int fim) {
      if (array != null && ini < fim) {
         int posicaoPivot = partition(array, ini, fim);
         quickSort(array, ini, posicaoPivot - 1);
         quickSort(array, posicaoPivot + 1, fim);
      }
   }

   private Integer partition(T[] array, Integer ini, Integer fim) {
      T pivot = array[ini];
      Integer i = ini;

      for (int j = i + 1; j <= array.length; j++) {
         if (array[j].compareTo(pivot) < 0) {
            i++;
            Util.swap(array, j, i);
         }
      }
      Util.swap(array, ini, i);
      return i;
   }
}
