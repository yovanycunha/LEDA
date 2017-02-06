package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   private static final int INVALID_INDEX = -1;
   private static final int ZERO = 0;

   public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
      super(size);
      hashFunction = new HashFunctionLinearProbing<T>(size, method);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {

      if (super.isFull()) {
         throw new HashtableOverflowException();
      }

      if (element != null || search(element) == null) {

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
            COLLISIONS++;

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
         while (probe < super.capacity() && table[hashIndex] != null && !deletedElement.equals(table[hashIndex])) {

            if (table[hashIndex].equals(element)) {
               return element;
            } else {
               probe++;
               hashIndex = this.hashFunc(element, probe);
            }
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
            } else {
               probe++;
               hashIndex = this.hashFunc(element, probe);
            }

         }
      }
      return INVALID_INDEX;
   }

   private int hashFunc(T element, int prob) {
      int hashIndex = ((HashFunctionLinearProbing<T>) super.hashFunction).hash(element, prob);
      return hashIndex;
   }

}
