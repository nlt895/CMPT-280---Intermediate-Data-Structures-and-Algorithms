/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
package lib280.tree;

import lib280.base.LinearIterator280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedBinaryTreeIterator280<I> extends ArrayedBinaryTreePosition280 implements LinearIterator280<I> {

	// This is a reference to the tree that created this iterator object.
	ArrayedBinaryTree280<I> tree;
	
	// An integer that represents the cursor position is inherited from
	// ArrayedBinaryTreePosition280.
	
	/**
	 * Create a new iterator from a given heap.
	 * @param t The heap for which to create a new iterator.
	 */
	public ArrayedBinaryTreeIterator280(ArrayedBinaryTree280<I> t) {
		super(t.currentNode);
		this.tree = t;
	}
	
	// TODO
	// Add methods from LinearIterator280 here.

	/**
	 * Returns the current item
	 * @precond The cursor is at a valid item
	 * @throws NoCurrentItem280Exception if there is no item exists 
	 * 		   at the current cursor
	 * @return Returns the item at the cursor
	 */
	@Override
	public I item() throws NoCurrentItem280Exception {
		// TODO 
		return this.tree.items[this.currentNode];
	}

	/**
	 * Check whether or not the current item is existed
	 * @return true if there exists an item, false otherwise
	 */
	@Override
	public boolean itemExists() {
		// TODO 
		return tree.count > 0 && (this.currentNode > 0 && this.currentNode <= tree.count);
	}

	/**
	 * Check whether or not the current position is before the start of the tree
	 */
	@Override
	public boolean before() {
		// TODO 
		return this.currentNode == 0;
	}

	/**
	 * Check whether or not the current position is after the end of the tree
	 */
	@Override
	public boolean after() {
		// TODO 
		return this.currentNode > tree.count;
	}

	/**
	 * Move forward to the next valid item
	 * @precond The cursor is not at after position 
	 * @throws AfterTheEnd280Exception
	 */
	@Override
	public void goForth() throws AfterTheEnd280Exception {
		// TODO 
		if(this.after()) throw new AfterTheEnd280Exception ("ERROR: Cannot move the cursor when it is at after position.");
		this.currentNode ++;
	}

	/**
	 * Go to the first item in the tree
	 * @precond The tree is not empty
	 * @throws ContainerEmpty280Exception 
	 */
	@Override
	public void goFirst() throws ContainerEmpty280Exception {
		// TODO 
		if(tree.isEmpty()) throw new ContainerEmpty280Exception("ERROR: Cannot go to the first item of an empty tree.");
		this.currentNode = 1;
	}

	/**
	 * Move to the position that is prior to the first element of the tree
	 */
	@Override
	public void goBefore() {
		// TODO 
		this.currentNode = 0;
	}

	/**
	 * Move to the position that is after the last element of the tree
	 */
	@Override
	public void goAfter() {
		// TODO 
		this.currentNode = tree.count + 1;
	}


}
