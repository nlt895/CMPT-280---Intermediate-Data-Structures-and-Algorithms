package lib280.tree;

import lib280.base.Container280;
import lib280.exception.NoCurrentItem280Exception;

/**
 * @author eramian
 *
 */
public abstract class ArrayedBinaryTree280<I> implements Container280 {

	protected int currentNode;		// Index of the node corresponding to the current cursor position.
	protected int capacity;			// Maximum number of elements in the tree.
	protected int count;			// Current number of elements in the tree.
	
	protected I items[];
	
	@SuppressWarnings("unchecked")
	/**
	 * Constructor.
	 * 
	 * @param cap Maximum number of elements that can be in the tree.
	 */
	public ArrayedBinaryTree280(int cap) {
		capacity = cap;
		currentNode = 0;
		count = 0;
		items = (I[]) new Object[capacity+1];
	}
	
	/**
	 * Gets the index of the left child of the node at index 'node'.
	 */
	protected int findLeftChild(int node) {
		return node * 2;
	}
	
	/**
	 * Gets the index of the right child of the node at index 'node'.
	 */
	protected int findRightChild(int node) {
		return node * 2 + 1;
	}
	
	/**
	 * Gets the index of the parent of the node at index 'node'.
	 */
	protected int findParent(int node) {
		return node / 2;
	}


	


	/** 
	 * Remove the current item from the tree.
	 * 
	 * @precond The tree must not be empty.
	 * @precond There must be an item at the cursor.  
	 * @throws ContainerEmpty280Exception if the tree is empty.
	 * @throws NoCurrentItem280Exception if the cursor is not positioned at an item.
	 */
//	public void removeItem() throws ContainerEmpty280Exception, NoCurrentItem280Exception {
//		
//		if(this.isEmpty() ) throw new ContainerEmpty280Exception();
//		if(!itemExists() ) throw new NoCurrentItem280Exception();
//		
//		// If there was more than one item, and we aren't deleting the last item,
//		// copy the last item in the array to the current position.
//		if( count > 1 && currentNode < count ) {
//			items[currentNode] = items[count];
//		}
//		
//		count--;
//		
//		// If we deleted the last item, make the previous item the new position.
//		if( currentNode == count+1 ) currentNode--;
//	}	
	
	
	/**
	 * Get the item at the cursor.
	 * 
	 * @precond The tree must not be empty.  The cursor position must be valid.
	 * @throws NoCurrentItem280Exception if the cursor is not positioned at an item.
	 * @return The item at the cursor.
	 */
	public I item() throws NoCurrentItem280Exception{
		
		if(!itemExists() ) throw new NoCurrentItem280Exception();
		else return items[currentNode];
	}
	
	/**
	 * Determines if an item exists.
	 * 
	 * @return true if there is an item at the cursor, false otherwise.
	 */
	public boolean itemExists(){
		return count > 0 && (currentNode > 0 && currentNode <= count);
	}
	
	
	/**
	 * Get the maximum capacity of the tree.
	 * 
	 * @return The maximum number of items the tree can store.
	 */
	public int capacity() {
		return capacity;
	}
	
	/** 
	 * Get the number of items in the tree.
	 * 
	 * @return The number of items in the tree.
	 * 
	 */
	public int count() {
		return count;
	}
	
	/** Empty the tree
	 * 
	 *  Remove all elements from the tree.
	 */
	public void clear() {
		count = 0;
		currentNode = 0;
	}
	
	/**
	 * Returns a shallow clone of the tree.  The new tree contains
	 * copies of item references, not new items.
	 * 
	 * @return A reference to the new copy.
	 */
	@SuppressWarnings("unchecked")
	public ArrayedBinaryTree280<I> clone() {
		ArrayedBinaryTree280<I> temp;
		try { 
			temp = (ArrayedBinaryTree280<I>) super.clone();
		}
		catch (CloneNotSupportedException e) {
			temp = null;
		}
		return temp;
	}
	
	
	/**
	 * Determine if the tree is empty.
	 * 
	 * @return true if the tree is empty.  false otherwise.
	 */
	public boolean isEmpty() {
		return count == 0 && currentNode == 0;
	}
	
	/**
	 * Determine if the tree is full.
	 * 
	 * @return true if the tree is full.  false otherwise.
	 */
	public boolean isFull() {
		return count == capacity;
	}
	

	public String toString() {
		String out = "";
		for(int i=1; i <= count; i++) {
			out += items[i] + ", ";
		}
		
		out += "\n" + "Cursor is on item with array index " + this.currentNode + " (item "+this.items[this.currentNode]+")";
		
		return out;
	}


}
