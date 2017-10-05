/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
package lib280.tree;


import lib280.tree.ArrayedBinaryTree280;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.InvalidState280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedHeap280<I extends Comparable<? super I>> extends ArrayedBinaryTree280<I> {
	
	@SuppressWarnings("unchecked")
	/**
	 * A constructor to create a new heap
	 * @param cap The capacity for the heap
	 */
	public ArrayedHeap280(int cap) {
		super(cap);
		items = (I[]) new Comparable[capacity+1];  
	}
	
	/**
	 * Insert an item into the tree
	 * @param item The item to be inserted
	 * @precond The tree is not full
	 * @throws InvalidState280Exception
	 */
	public void insert(I item) throws InvalidState280Exception {
		if( isFull() ) throw new InvalidState280Exception("ERROR: Cannot insert item into a full tree.");
		else {
			// Insert normally into the tree
			count ++;
			items[count] = item;
		}
		// The cursor is now at the newly inserted item
		int pos = count;
		
		// If the current item is larger than its parent
		while(pos > 1 && items[pos].compareTo(items[findParent(pos)]) > 0) {
			int parent = findParent(pos);
			
			//Swap them
			I tmp = items[parent];
			items[parent] = items[pos];
			items[pos] = tmp;
			pos = findParent(pos);
		}
	}
	
	/**
	 * Delete the item at the top of the heap
	 * @precond The tree cannot be empty
	 * 			The cursor must be at the current item
	 * @throws NoCurrentItem280Exception
	 */
	public void removeItem() throws NoCurrentItem280Exception, ContainerEmpty280Exception {
		if(this.isEmpty()) throw new ContainerEmpty280Exception();
		if (count == 0) throw new NoCurrentItem280Exception();
		this.currentNode = 1;
		// If there is more than one item, and we aren't deleting the last item
		if(this.count > 1 && this.currentNode < this.count) {
			// Copy the last item in the array to the current position
			this.items[currentNode] = this.items[count];
		}
		this.count--;
		// Deleting the last item
		if( currentNode == count+1 ) currentNode--;
		
		int pos = 1;
		while( this.findLeftChild(pos) <= this.count ) {  
			//Find right and left child
			int leftChild = this.findLeftChild(pos);
			int rightChild = this.findRightChild(pos);
			
			// If the right child is larger than left child, then choose the right child
			if( rightChild <= this.count && items[leftChild].compareTo(items[rightChild]) < 0 ) leftChild++;
			// If the parent is smaller than the root node
			if( items[pos].compareTo(items[leftChild]) < 0 ) {
				// Swap them
				I tmp = items[pos];
				items[pos] = items[leftChild];
				items[leftChild] = tmp;
				pos = leftChild;
			}
			else return;
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayedHeap280<Integer> T = new ArrayedHeap280<Integer>(10);
		
		//Test precondition
		System.out.println("Deleting an item from an empty heap.");
		try {
			T.removeItem();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( ContainerEmpty280Exception e ) {
			System.out.println("Exeception is caught.");
		}
		
		//Test insertion
		T.insert(90);
		T.insert(85);
		T.insert(80);
		T.insert(40);
		T.insert(65);
		T.insert(55);

		System.out.println("List should be: 90, 85, 80, 40, 65, 55");
		System.out.print(  "     and it is: ");
		System.out.println(T);
		//Test deletion 
		T.removeItem();

		System.out.println("List should be: 85, 80, 40, 65, 55");
		System.out.print(  "     and it is: ");
		System.out.println(T);
		//Test deletion
		T.removeItem();
		
		System.out.println("List should be: 80, 40, 65, 55");
		System.out.print(  "     and it is: ");
		System.out.println(T);
		
		System.out.println("Regression test complete.");
	}
}
	

