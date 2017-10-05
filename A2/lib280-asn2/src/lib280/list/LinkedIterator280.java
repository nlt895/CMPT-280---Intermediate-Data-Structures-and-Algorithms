/* LinkedIterator280.java
 * ---------------------------------------------
 * Copyright (c) 2010 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */
 
package lib280.list;

import lib280.exception.*;
import lib280.list.LinkedList280;
import lib280.base.*;

/**	A LinearIterator280 for linked lists.  It utilizes a cursor to 
	keep track of the current item, and has functions to move 
	forward and to the front of the list.  It also has functions to 
	determine if it is before the start or after the end of the 
	structure. */
public class LinkedIterator280<I> implements LinearIterator280<I>
{
	/**	List being iterated. */  
	protected LinkedList280<I> list;
  
	/**	The node with the current item. */
	protected LinkedNode280<I> cur;
  
	/**	The node previous to the current node. */
	protected LinkedNode280<I> prev;
  
	/**	Create a new iterator for the newList. <br>
		Analysis : Time = O(1) 
		@param newList list to be iterated */
	public LinkedIterator280(LinkedList280<I> newList)
	{
		list = newList;
		if(!list.isEmpty()) goFirst();
	}
  
	/**	Create a new iterator at a specific position in the newList. <br>
		Analysis : Time = O(1)
		@param newList list to be iterated
		@param initialPrev the previous node for the initial position
		@param initialCur the current node for the initial position */
	public LinkedIterator280(LinkedList280<I> newList, 
		LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		list = (LinkedList280<I>) newList;
		prev = (LinkedNode280<I>) initialPrev;
		cur = (LinkedNode280<I>) initialCur;
	}
    
	/**	Is the current position before the first item in the structure?. <br>
		Analysis : Time = O(1) */
	public boolean before()
	{
		return (prev==null) && (cur==null);
	}
  
	/**	Is the current position after the last item in  the structure?. <br>
		Analysis : Time = O(1) */
	public boolean after()
	{
		return (cur==null) && (prev!=null || list.isEmpty());
	}
  
	/**	Is there a current item?. <br>
		Analysis : Time = O(1) */
	public boolean itemExists()
	{
		return !before() && !after();
	}

	/**	The current item. <br>
		Analysis : Time = O(1)
		@precond itemExists() 
		@throws ItemNotFound280Exception
	 */
	public I item() throws ItemNotFound280Exception 
	{
		if (!itemExists())
			throw new ItemNotFound280Exception("A current item must exist");  

		return cur.item();
	}
  
	/**	Go to the first item in the data structure. <br>
		Analysis : Time = O(1) 
		@precond The list is not empty.
		*/
	public void goFirst() throws ContainerEmpty280Exception
	{
		if(this.list.isEmpty()) throw new ContainerEmpty280Exception("Cannot move to first element of an empty list.");
		
		prev = null;
		cur = list.firstNode();
	}
  
	/**	Move prior to the first item. <br>
		Analysis : Time = O(1) */
	public void goBefore()
	{
		cur = null;
		prev = null;
	}
  
	/**	Advance one item forth in the data structure. <br>
		Analysis : Time = O(1) 
		@precond !after() 
		@throws AfterTheEnd280Exception
	*/
	public void goForth() throws AfterTheEnd280Exception
	{
		if (after())
			throw new AfterTheEnd280Exception("Cannot advance to next item when already after.");

		if (before())
			goFirst();
		else
		{
			prev = cur;
			cur = cur.nextNode();
		}
	}
  
	/**	Go after the last item in the data sturcture. <br>
		Analysis : Time = O(n), where n = length of the list */
	public void goAfter()
	{
		cur = null;
		prev = ((LinkedList280<I>)list).lastNode();
	}

	/**	String listing of the items in the tree. <br> 
		Analysis: Time = O(n), where n = length of the list */
	public String toString()
	{
		return list.toString();
	}

	/**	A shallow clone of this object. <br> 
		Analysis: Time = O(1) */
	@SuppressWarnings("unchecked")
	public LinkedIterator280<I> clone()
	{
		try
		{
			return (LinkedIterator280<I>)super.clone();
		} catch (CloneNotSupportedException e)
		{
			// Should not occur: this is a Cursor280, which implements Cloneable 
			e.printStackTrace();
			return null;
		}
	}

	/**	Is this cursor pointing to the same location as other?. <br>
		Analysis: Time = O(1)
		@param other The object to compare to */
	@SuppressWarnings("unchecked")
	public boolean equals(Object other)
	{
		LinkedIterator280<I> otherIter = (LinkedIterator280<I>) other;
		return (otherIter.cur == cur) && (otherIter.prev == prev) && (otherIter.list == list);
	}

	/**	A unique class identifier for serializing and deserializing. */
	static final long serialVersionUID = 1l;
}
