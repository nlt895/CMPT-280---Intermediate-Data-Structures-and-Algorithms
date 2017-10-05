package lib280.list;
import lib280.exception.*;
import lib280.base.*;

/**	A LinkedIterator which has functions to move forward and back, 
	and to the first and last items of the list.  It keeps track of 
	the current item, and also has functions to determine if it is 
	before the start or after the end of the list. */
public class BilinkedIterator280<I> extends LinkedIterator280<I> implements BilinearIterator280<I>
{

	/**	Constructor creates a new iterator for list 'list'. <br>
		Analysis : Time = O(1) 
		@param list list to be iterated */
	public BilinkedIterator280(BilinkedList280<I> list)
	{
		super(list);
	}

	/**	Create a new iterator at a specific position in the newList. <br>
		Analysis : Time = O(1)
		@param newList list to be iterated
		@param initialPrev the previous node for the initial position
		@param initialCur the current node for the initial position */
	public BilinkedIterator280(BilinkedList280<I> newList, 
			LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		super(newList, initialPrev, initialCur);
	}
    

	/**
	 * Move the cursor to the last element in the list.
	 * @precond The list is not empty.
	 */
    @Override
	public void  goLast() throws ContainerEmpty280Exception
	{
		if( this.list.isEmpty() ) throw new ContainerEmpty280Exception("Cannot position cursor at last element of an empty list.");
                this.cur = this.list.head;
                while (this.cur != this.list.lastNode()) {
                    this.prev = this.cur;
                    this.cur = this.cur.nextNode();
                }
	}

	
	/**
	 * Move the cursor one element closer to the beginning of the list
	 * @precond before() - the cursor cannot already be before the first element.
	 */
    @Override
	public void goBack() throws BeforeTheStart280Exception
	{
		if ( this.list.before() ) throw new BeforeTheStart280Exception("Cannot move back, because the element doesn't exist");
                if (this.cur == this.list.head) {
                    this.cur = null;
                    this.prev = null;
                } else {
                    this.cur = this.prev;
                    if (this.prev == this.list.head) this.prev = null;
                    else {
                        this.prev = this.list.head;
                        while (this.prev.nextNode() != this.cur) {
                            this.prev = this.prev.nextNode();
                        }
                    }
                }
        }

	/**	A shallow clone of this object. <br> 
	Analysis: Time = O(1) */
	public BilinkedIterator280<I> clone()
	{
		return (BilinkedIterator280<I>) super.clone();
	}


} 
