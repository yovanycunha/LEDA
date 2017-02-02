package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

   private static final int ZERO = 0;

   /**
    * A hash table with closed address works with a hash function with closed
    * address. Such a function can follow one of these methods: DIVISION or
    * MULTIPLICATION. In the DIVISION method, it is useful to change the size
    * of the table to an integer that is prime. This can be achieved by
    * producing such a prime number that is bigger and close to the desired
    * size.
    * 
    * For doing that, you have auxiliary methods: Util.isPrime and
    * getPrimeAbove as documented bellow.
    * 
    * The length of the internal table must be the immediate prime number
    * greater than the given size. For example, if size=10 then the length must
    * be 11. If size=20, the length must be 23. You must implement this idea in
    * the auxiliary method getPrimeAbove(int size) and use it.
    * 
    * @param desiredSize
    * @param method
    */

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
      int realSize = desiredSize;

      if (method == HashFunctionClosedAddressMethod.DIVISION) {
         realSize = this.getPrimeAbove(desiredSize); // real size must the
         // the immediate prime
         // above
      }
      initiateInternalTable(realSize);
      HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
      this.hashFunction = function;
   }

   // AUXILIARY
   /**
    * It returns the prime number that is closest (and greater) to the given
    * number. You can use the method Util.isPrime to check if a number is
    * prime.
    */
   int getPrimeAbove(int number) {

      int prime = number;

      /*
       * Se prime for um número par ele é incrementado pois não faz sentido
       * testar se um número par é primo.
       */
      if (prime % 2 == 0) {
         prime++;
      }

      /*
       * Enquanto o prime não for primo ele será incrementado de 2 em 2 para
       * que não sejam testados números pares.
       */
      while (!Util.isPrime(prime)) {
         prime += 2;
      }

      return prime;

   }

   @Override
   public void insert(T element) {

      if (element != null) {

         int hash = hashIt(element);

         int index = searchPosElem(element, hash);

         if (!this.containsElem(index)) {
            insertNew(element, hash);
         } else {
            ((LinkedList<T>) this.table[hash]).set(index, element);
         }

      }

   }

   @Override
   public void remove(T element) {

      if (element != null) {

         int hash = hashIt(element);
         int index = searchPosElem(element, hash);

         if (this.containsElem(index)) {
            ((LinkedList<T>) this.table[hash]).remove(element);
            this.elements--;

            if (((LinkedList<T>) this.table[hash]).size() > 0) {
               this.COLLISIONS--;
            }
         }
      }
   }

   @Override
   public T search(T element) {
      int hash = hashIt(element);
      int index = searchPosElem(element, hash);
      return (this.containsElem(index)) ? element : null;
   }

   @Override
   public int indexOf(T element) {

      if (element == null) {
         return -1;
      }

      int hash = hashIt(element);
      int index = searchPosElem(element, hash);
      if (!this.containsElem(index)) {
         return -1;
      } else {
         return hash;
      }

   }

   private int hashIt(T element) {
      int hash = ((HashFunctionClosedAddress) this.hashFunction).hash(element);
      return hash;
   }

   private void insertNew(T element, int hash) {

      if (this.table[hash] == null) {
         this.table[hash] = new LinkedList<T>();
      }
      if (((LinkedList<T>) this.table[hash]).size() > ZERO) {
         this.COLLISIONS++;
      }

      ((LinkedList<T>) this.table[hash]).add(element);
      this.elements++;
   }

   private boolean containsElem(int index) {
      return (index != -1);
   }

   private int searchPosElem(T element, int hash) {

      if (this.table[hash] == null) {
         return -1;
      }

      for (int index = 0; index < ((LinkedList<T>) this.table[hash]).size(); index++) {
         T elem = ((LinkedList<T>) this.table[hash]).get(index);
         if (elem.hashCode() == element.hashCode()) {
            return index;
         }
      }

      return -1;
   }

}
