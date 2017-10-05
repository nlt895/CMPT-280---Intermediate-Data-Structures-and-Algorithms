/* BilinearIterator280.java
 * ---------------------------------------------
 * Copyright (c) 2004 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */
 
package lib280.base;

import lib280.exception.BeforeTheStart280Exception;

/**	An Iterator which has routines to move forward and back, 
	and to the first and last items of a collection.  It keeps track of 
	the current item, and also has routines to determine if it is 
	before the start or after the end of the collection. */
public interface BilinearIterator280<I> extends LinearIterator280<I>
{
  
	/**	Go to last item of the data structure. */
	public void goLast();
 
	/**	Move back one item in the data structure. <br>
		PRECONDITION: <br>
		<ul>
			!before()
		</ul> */
	public void goBack() throws BeforeTheStart280Exception;
 
} 
