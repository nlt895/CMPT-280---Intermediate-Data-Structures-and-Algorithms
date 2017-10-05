/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
package lib280.tree;

import lib280.exception.NoCurrentItem280Exception;

public class IterableArrayedHeap280<I extends Comparable<? super I>> extends ArrayedHeap280<I>  {

	/**
	 * Create an iterable heap with a given capacity.
	 * @param cap The maximum number of elements that can be in the heap.
	 */
	public IterableArrayedHeap280(int cap) {
		super(cap);
	}

	// TODO
	// Add iterator() and deleteAtPosition() methods here.

	/**
	 * @return a new ArrayBinaryTreeIterator280 that is based on a array heap
	 */
	public ArrayedBinaryTreeIterator280<I> iterator() {
		return new ArrayedBinaryTreeIterator280<I>(this);
	}
	
	/**
	 * Deletes the item at the position which is pointed by an iterator
	 * @param iter The iterator that will point to the item to be deleted
	 * @throws NoCurrentItem280Exception if the current position is not existed 
	 */
	public void deleteAtPosition(ArrayedBinaryTreeIterator280<I> iter) throws NoCurrentItem280Exception {
		
		if (iter.currentNode > this.count) throw new NoCurrentItem280Exception();
		
		// If there is more than one item, and we aren't deleting the last item
		if(this.count > 1 && this.itemExists()) {
			// Copy the last item in the array to the current position
			this.items[iter.currentNode] = this.items[count];
		}
		this.count--;
		
		// If we deleted the last remaining item, make the the current item
		// invalid, and we're done.
		if( this.count == 0 ) {
			this.currentNode = 0;
			return;
		}
		
		// Propagate the new root down the tree.
		int n = currentNode;
				
		// While offset n has a left child...
		while (findLeftChild(n) <= count) {
			// Select the left child.
			int child = findLeftChild(n);

			// If the right child exists and is larger, select it instead.
			if (child + 1 <= count && items[child].compareTo(items[child + 1]) < 0)
				child++;

			// If the parent is smaller than the root...
			if (items[n].compareTo(items[child]) < 0) {
				// Swap them.
				I temp = items[n];
				items[n] = items[child];
				items[child] = temp;
				n = child;
			} else
				return;
		}
	}

}
