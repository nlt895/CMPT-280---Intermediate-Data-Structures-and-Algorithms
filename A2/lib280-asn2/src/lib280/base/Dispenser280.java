/* DispenserUos.java
 * ---------------------------------------------
 * Copyright (c) 2004,2010 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */
 
package lib280.base;

import lib280.exception.*;


/**	A Container class which keeps track of the current item as 
	insertions are made.  Only the current item can be deleted.  
	The current item depends on the type of dispenser. */
public interface Dispenser280<I> extends Container280, Cursor280<I>
{
	/**	Insert x into the data structure.
	 * @precond !isFull() and !has(x)
	 * @param x item to be inserted into the data structure 
	 */
	public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception;
 
	/**	Delete current item from the data structure. 
	 * @precond	itemExists() 
	 */
	public void deleteItem() throws NoCurrentItem280Exception;
}
