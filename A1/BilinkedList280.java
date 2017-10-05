package lib280.list;
import lib280.base.*;
import lib280.exception.*;

/**	This list class incorporates the functions of an iterated 
	dictionary such as has, obtain, search, goFirst, goForth, 
	deleteItem, etc.  It also has the capabilities to iterate backwards 
	in the list, goLast and goBack. */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I>
{
	/* 	Note that because firstRemainder() and remainder() should not cut links of the original list,
		the previous node reference of firstNode is not always correct.
		Also, the instance variable prev is generally kept up to date, but may not always be correct.  
		Use previousNode() instead! */

	/**	Construct an empty list.
		Analysis: Time = O(1) */
	public BilinkedList280()
	{
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
    @Override
	protected BilinkedNode280<I> createNewNode(I item)
	{
    	return new BilinkedNode280<I>(item);
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
    @Override
	public void insertFirst(I x) 
	{
		BilinkedNode280<I> newNode = this.createNewNode(x);                
        newNode.setNextNode(this.head);
        newNode.setPreviousNode(null);
		
        // If the cursor is at the first node, cursor predecessor becomes the new node.
		if( this.position == this.head ) this.prevPosition = newNode;
				
		//if the list is empty, the new item also becomes the tail.
		if( this.isEmpty() ) this.tail = newNode;
		this.head = newNode;
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insert(I x) 
	{
		this.insertFirst(x);
	}

	/**
	 * Insert an item before the current position.
	 * @param x - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if( this.before() ) throw new InvalidState280Exception("Cannot insertBefore() when the cursor is already before the first element.");
		
		// If the item goes at the beginning or the end, handle those special cases.
		if( this.head == position ) {
			insertFirst(x);  // special case - inserting before first element
		}
		else if( this.after() ) {
			insertLast(x);   // special case - inserting at the end
		}
		else {
			// Otherwise, insert the node between the current position and the previous position.
			BilinkedNode280<I> newNode = createNewNode(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>)this.position).setPreviousNode(newNode);
			
			// since position didn't change, but we changed it's predecessor, prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;			
		}
	}
	
	
	/**	Insert x before the current position and make it current item. <br>
		Analysis: Time = O(1)
		@param x item to be inserted before the current position */
	public void insertPriorGo(I x) 
	{
		this.insertBefore(x);
		this.goBack();
	}

	/**	Insert x after the current item. <br>
		Analysis: Time = O(1) 
		@param x item to be inserted after the current position */
	public void insertNext(I x) 
	{
		if (isEmpty() || before())
			insertFirst(x); 
		else if (this.position==lastNode())
			insertLast(x); 
		else if (after()) // if after then have to deal with previous node  
		{
			insertLast(x); 
			this.position = this.prevPosition.nextNode();
		}
		else // in the list, so create a node and set the pointers to the new node 
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>)this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list 
	 */
    @Override
	public void insertLast(I x) 
	{
		BilinkedNode280<I> newNode = createNewNode(x);
        newNode.setNextNode(null);
		
		// If the cursor is after(), then cursor predecessor becomes the new node.
		if( this.after() ) this.prevPosition = newNode;
		
		// If list is empty, handle special case
		if( this.isEmpty() ) {
			this.head = newNode;
			this.tail = newNode;
		}
		else {
			this.tail.setNextNode(newNode);
			newNode.setPreviousNode((BilinkedNode280<I>) this.tail);
			this.tail = newNode;
		}
	}

	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
    @Override
	public void deleteItem() throws NoCurrentItem280Exception
	{
		if(!this.itemExists()) throw new NoCurrentItem280Exception("There is no item at the cursor to delete.");
		
		// If we are deleting the first item
		if( this.position == this.head ) {
			this.deleteFirst();
			this.position = this.head;
		}
		else {
			// Set the previous node to point to the successor node. 
			this.prevPosition.setNextNode(this.position.nextNode());
			if (this.position.nextNode() != null) {
				((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode((BilinkedNode280<I>) this.prevPosition);
			}
			// Reset the tail reference if we deleted the last node.
			if( this.position == this.tail ) {
				this.tail = this.prevPosition;
			}
			this.position = this.position.nextNode();
		}
	}

	@Override
	public void delete(I x) throws ItemNotFound280Exception {
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete from an empty list.");

		// Save cursor position
		LinkedIterator280<I> savePos = (LinkedIterator280<I>)this.currentPosition();
		
		// Find the item to be deleted.
		search(x);
		if( !this.itemExists() ) throw new ItemNotFound280Exception("Item to be deleted wasn't in the list.");

		// If we are about to delete the item that the cursor was pointing at,
		// advance the cursor in the saved position, but leave the predecessor where
		// it is because it will remain the predecessor.
		if( this.position == savePos.cur ) savePos.cur = savePos.cur.nextNode();
		
		// If we are about to delete the predecessor to the cursor, the predecessor 
		// must be moved back one item.
		if( this.position == savePos.prev ) {
			
			// If savePos.prev is the first node, then the first node is being deleted
			// and savePos.prev has to be null.
			if( savePos.prev == this.head ) savePos.prev = null;
			else {
				// Otherwise, Find the node preceding savePos.prev
				LinkedNode280<I> tmp = this.head;
				while(tmp.nextNode() != savePos.prev) tmp = tmp.nextNode();
				
				// Update the cursor position to be restored.
				savePos.prev = tmp;
			}
		}
				
		// Unlink the node to be deleted.
		if( this.prevPosition != null)
			// Set previous node to point to next node.
			// Only do this if the node we are deleting is not the first one.
			this.prevPosition.setNextNode(this.position.nextNode());
		
		if( this.position.nextNode() != null )
			// Set next node to point to previous node 
			// But only do this if we are not deleting the last node.
			((BilinkedNode280<I>)this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>)this.position).previousNode());
		
		// If we deleted the first or last node (or both, in the case
		// that the list only contained one element), update head/tail.
		if( this.position == this.head ) this.head = this.head.nextNode();
		if( this.position == this.tail ) this.tail = this.prevPosition;
		
		// Clean up references in the node being deleted.
		this.position.setNextNode(null);
		((BilinkedNode280<I>)this.position).setPreviousNode(null);
		
		// Restore the old, possibly modified cursor.
		this.goPosition(savePos);
		
	}
	
	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
    @Override
	public void deleteFirst() throws ContainerEmpty280Exception
	{
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		
		// If the cursor is on the second node, set the prev pointer to null.
		if( this.prevPosition == this.head ) this.prevPosition = null;
		// Otherwise, if the cursor is on the first node, set the cursor to the next node.
		else if (this.position == this.head )  this.position = this.position.nextNode();
		
		// If we're deleting the last item, set the tail to null.
		// Setting the head to null gets handled automatically in the following
		// unlinking.
		if( this.head == this.tail ) this.tail = null;
		
		// Unlink the first node.
		BilinkedNode280<I> oldhead = (BilinkedNode280<I>) this.head;
		this.head = this.head.nextNode();
		oldhead.setNextNode(null);
	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
    @Override
	public void deleteLast() throws ContainerEmpty280Exception
	{
		// Special cases if there are 0 or 1 nodes.
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		else if( this.head != null && this.head == this.tail ) this.deleteFirst();
		else {
			// There are at least two nodes.
		
			// If the cursor is on the last node, we need to update the cursor.
			if( this.position == this.tail ) {
				// Find the node prior to this.position
				BilinkedNode280<I> newPrev = (BilinkedNode280<I>) this.head;
				while( newPrev.nextNode() != this.prevPosition) newPrev = (BilinkedNode280<I>) newPrev.nextNode();
				this.position = this.prevPosition;
				this.prevPosition = newPrev;
			}
		
			// Find the second-last node -- note this makes the deleteLast() algorithm O(n)
			BilinkedNode280<I> penultimate = (BilinkedNode280<I>) this.head;
			while(penultimate.nextNode() != this.tail) penultimate = (BilinkedNode280<I>) penultimate.nextNode();
		
			// If the cursor is in the after() position, then prevPosition
			// has to become the second last node.
			if( this.after() ) {
				this.prevPosition = penultimate;
			}

			// Unlink the last node.
			penultimate.setNextNode(null);
			this.tail = penultimate;
		}
	}

	
	/**
	 * Move the cursor to the last item in the list.
	 * @precond The list is not empty.
	 */
    @Override
	public void goLast() throws ContainerEmpty280Exception
	{
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot position cursor at last element of an empty list.");
			this.position = this.head;
            while (this.position != this.tail) {
            	this.prevPosition = this.position;
                this.position = this.position.nextNode();
                }
	}
  
	/**	Move back one item in the list. 
		Analysis: Time = O(1)
		@precond before() 
	 */
    @Override
	public void goBack() throws BeforeTheStart280Exception
	{
		if ( before() ) throw new BeforeTheStart280Exception("Cannot move back, because the element doesn't exist");
		if (this.position == this.head) 
		{
			this.position = null;
            this.prevPosition = null;
		} else {
            	this.position = this.prevPosition;
            	if (this.prevPosition == this.head) this.prevPosition = null;
            	else 
            	{
            		this.prevPosition = this.head;                    
            		while (this.prevPosition.nextNode() != this.position) 
            		{
            			this.prevPosition = this.prevPosition.nextNode();
            		}
                }
            }
	}

	/**	Iterator for list initialized to first item. 
		Analysis: Time = O(1) 
	*/
	public BilinkedIterator280<I> iterator()
	{
		return new BilinkedIterator280<I>(this);
	}

	/**	Go to the position in the list specified by c. <br>
		Analysis: Time = O(1) 
		@param c position to which to go */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c)
	{
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = (BilinkedNode280<I>) lc.cur;
		this.prevPosition = (BilinkedNode280<I>) lc.prev;
	}

	/**	The current position in this list. 
		Analysis: Time = O(1) */
	public BilinkedIterator280<I> currentPosition()
	{
		return  new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}

	
  
	/**	A shallow clone of this object. 
		Analysis: Time = O(1) */
	public BilinkedList280<I> clone() throws CloneNotSupportedException
	{
		return (BilinkedList280<I>) super.clone();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// create BilinkedList280 
        BilinkedList280<String> list = new BilinkedList280<String>();
        // test insertFirst(), and insertLast() methods
        System.out.println("Test insertFirst() and insertLast() methods");

        list.insertFirst("A");
        list.insertFirst("B");
        list.insertFirst("C");
        list.insertLast("D");
        list.insertLast("E");
        list.insertLast("F");

        System.out.println("List should be: C, B, A, D, E, F, ");
        System.out.print("     and it is: ");
        System.out.println(list);
                
        // test deleteItem(), deleteFirst() and deleteLast() methods
        System.out.println("Test deleteFirst() and deleteLast() methods");
        list.deleteFirst();
        System.out.println(list);
                
        list.deleteLast();
        System.out.println(list);
                
        System.out.println("List should be: B, A, D, E,");
        System.out.print("     and it is: ");
        System.out.println(list);
                
        // test goLast(), goBack() and deleteItem() methods
        System.out.println("Test goLast(), goBack() and deleteItem() methods");
        System.out.println("Go to last");
        list.goLast();
        System.out.print("Cursor should be at E ....");
        if (list.position.item().equals("E")) System.out.println("and it is E.  OK!");
        else System.out.println("and it is not.  ERROR!");
                
        System.out.println("Delete this Item and move cursor to the last Item");
        list.deleteItem();
        list.goBack();
                
        System.out.println("List should be: B, A, D,");
        System.out.print("     and it is: ");
        System.out.println(list);
                
        System.out.println("Go to back and go to back");
        list.goBack();
        list.goBack();
        System.out.print("Cursor should be at B ....");
        if (list.position.item().equals("B")) System.out.println("and it is B.  OK!");
        else System.out.println("and it is not.  ERROR!");
                
        list.deleteItem();
        System.out.println("Delete this Item");
                
        System.out.println("List should be: A, D,");
        System.out.print("     and it is: ");
        System.out.println(list);
                
        // delete other item
        System.out.println("Delete other items");
        list.deleteFirst();
        list.deleteLast();
                
        System.out.println("List should be empty");
        System.out.print("     and it is: ");
        System.out.println(list);
                
        // Test deleting from an empty list.
        System.out.println("Test deleting from an empty list.");
                
        System.out.println("Deleting first item from empty list.");
        try {
        	list.deleteFirst();
            System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( ContainerEmpty280Exception e ) {
			System.out.println("Caught exception.  OK!");
		}
                
        System.out.println("Deleting last item from empty list.");
		try {
			list.deleteLast();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( ContainerEmpty280Exception e ) {
			System.out.println("Caught exception. OK!");
		}
                
        System.out.println("Delete the item at which the cursor is positioned from empty list.");
		try {
			list.deleteItem();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( NoCurrentItem280Exception e ) {
			System.out.println("Caught exception. OK!");
		}
                
        System.out.println("Trying move cursor to last item in empty list.");
		try {
			list.goLast();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( ContainerEmpty280Exception e ) {
			System.out.println("Caught exception. OK!");
		}
                
        System.out.println("Trying move cursor to back in empty list.");
		try {
			list.goBack();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");			
		}
		catch( BeforeTheStart280Exception e ) {
			System.out.println("Caught exception. OK!");
		}
                
		// Test BilinkedIterator280<I>: goLast() and goBack() methods
        System.out.println("Test BilinkedIterator280<I>: goLast() and goBack() methods");
        BilinkedIterator280<String> iterator280 = new BilinkedIterator280<String>(list);
                
        System.out.println("InsertFirst A, B, C");
        list.insertFirst("A");
        list.insertFirst("B");
        list.insertFirst("C");
                
        System.out.println("List should be: C, B, A,");
		System.out.print("     and it is: ");
		System.out.println(list);
                
        System.out.println("Go to last");
        iterator280.goLast();
        System.out.print("Cursor should be at A ....");
        if (iterator280.cur.item().equals("A")) System.out.println("and it is A.  OK!");
		else System.out.println("and it is not.  ERROR!");
        System.out.println("Go to back and go to back");
        iterator280.goBack();
        iterator280.goBack();
        System.out.print("Cursor should be at C ....");
        if (iterator280.cur.item().equals("C")) System.out.println("and it is C.  OK!");
		else System.out.println("and it is not.  ERROR!");
	
	}
} 
