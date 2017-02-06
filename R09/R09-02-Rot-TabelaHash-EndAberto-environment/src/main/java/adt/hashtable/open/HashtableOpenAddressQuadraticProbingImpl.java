package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   private static final int INVALID_INDEX = -1;
   private static final int ZERO = 0;

   public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
      super(size);
      hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {

      if (super.isFull()) {
         throw new HashtableOverflowException();
      }
      if (element != null && this.search(element) == null) {

         int probe = ZERO;
         int hashIndex = this.hashFunc(element, probe);

         while (probe < super.capacity()) {

            if (table[hashIndex] == null || deletedElement.equals(table[hashIndex])) {
               table[hashIndex] = element;
               super.elements++;
               return;
            }
            probe++;
            hashIndex = this.hashFunc(element, probe);
            super.COLLISIONS++;
         }

      }

   }

   @Override
   public void remove(T element) {

      if (element != null) {

         if (this.indexOf(element) != INVALID_INDEX) {

            int hashIndex = this.indexOf(element);
            table[hashIndex] = super.deletedElement;
            super.elements--;
         }

      }

   }

   @Override
   public T search(T element) {
      if (element != null) {

         int probe = ZERO;
         int hashIndex = this.hashFunc(element, probe);

         while (probe < super.capacity() && table[hashIndex] != null && !deletedElement.equals(element)) {

            if (table[hashIndex].equals(element)) {
               return element;
            }

            probe++;
            hashIndex = this.hashFunc(element, probe);

         }
      }
      return null;

   }

   @Override
   public int indexOf(T element) {

      if (element != null) {
         int probe = ZERO;
         int hashIndex = this.hashFunc(element, probe);

         while (probe < super.capacity() && table[hashIndex] != null && !deletedElement.equals(table[hashIndex])) {

            if (table[hashIndex].equals(element)) {

               return hashIndex;
            }
            probe++;
            hashIndex = this.hashFunc(element, probe);
         }

      }
      return INVALID_INDEX;

   }

   private int hashFunc(T element, int probe) {

      int hashIndex = ((HashFunctionQuadraticProbing<T>) super.hashFunction).hash(element, probe);
      return hashIndex;
   }
}
