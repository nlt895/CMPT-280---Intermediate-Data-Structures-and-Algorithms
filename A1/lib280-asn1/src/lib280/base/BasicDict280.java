/* BasicDictUos.java
 * ---------------------------------------------
 * Copyright (c) 2004,2010 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */
 
package lib280.base;

import lib280.exception.*;

/**	A Container class that contains the most basic dictionary 
	capabilities: has, obtain, insert, delete, and isEmpty. 
	All classes that implement this interface will be bags. */
public interface BasicDict280<I> extends Membership280<I>
{
	/**	Retrieve an item from the dictionary with membershipEquals(item,y).
	 * @precond has(y)
	 * @param y item to obtain from the dictionary 
	 */
	public I obtain(I y) throws ItemNotFound280Exception;
 
	/**	Insert x into the dictionary.
	 * @precond !isFull() and !has(x)
	 * @param x item to be inserted into the dictionary 
	 */
	public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception;
 
	/**	Delete the item x.
	 * @precond has(x)
	 * @param x item to be deleted from the dictionary 
	 */
	public void delete(I x) throws ItemNotFound280Exception;
}
