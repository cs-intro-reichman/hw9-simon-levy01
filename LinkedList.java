/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		// loop over the list until the node at the given index, and update current to be that node
		Node current = this.first;
		for (int i = 0; i<index; i++){
			current = current.next;
		}
		return current;
	}
		/**
		 * Gets the first node of the list
		 * @return The first node of the list.
		 */		
		public Node getFirst() {
			return this.first;
		}

		/**
		 * Gets the last node of the list
		 * @return The last node of the list.
		 */		
		public Node getLast() {
			return this.last;
		}
	
		/**
		 * Gets the current size of the list
		 * @return The size of the list.
		 */		
		public int getSize() {
			return this.size;
		}
	
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node newNode = new Node(block);
		// if index is 0, add new node to start and update first to be the new node
		if (index == 0){
			newNode.next = this.first;
			this.first = newNode;
			// if size is 0, update last to be newnode (sinze list is now only the new node)
			if (size == 0){
				last = newNode;
			}
		// if index is size, then add the newnode to the end of the list , and update last
		} else if (index==size){
			this.last.next = newNode;
			this.last = newNode;
		// if index is between 0 and size, find node at the previous index
		// then insernt the new node between the node at index-1 and index
		} else {
			Node previous = getNode(index-1);
			newNode.next = previous.next;
			previous.next = newNode;
		}
		// increment list size
		size++;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node newNode = new Node(block);
		// if the list is empty, make the new node be the first and last
		if (size == 0){
			this.last = newNode;
			this.first = newNode;
		// if list has a last node, make the last node point to the new node, and update last
		} else {
			this.last.next = newNode;
			this.last = newNode;
		}
		//increment list size
		size++;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node newNode = new Node(block);
		// if the list is empty, make the new node be the first and last
		if (size == 0){
			this.last = newNode;
			this.first = newNode;
		// if the list has a first node, make the new node point to the first node, and update first
		} else {
			newNode.next = this.first;
			this.first = newNode;
		}
		//increment list size
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node current = getNode(index);
		return current.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node current = this.first;
		// iterate over the list, and check for a match at each step
		// if match is found, return its index, if not, update to the next node 
		// if the list is empty, the loop wont start, and will return -1
		for (int i = 0; i<size; i++){
			if (current.block.equals(block)){
				return i;
			}
			current = current.next;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		//if node is null, do nothing
		if (node == null){
			return;
		//if node to remove is first, update first to be the one it was pointing to
		} else if(node == this.first){
			this.first = this.first.next;
			//if list is now empty, update last to be null
			if (this.first == null){
				this.last = null;
			}
			//decrement size
			size -- ;
			return;
		}
		//make a new node, check all nodes in the list to see if they are the node to remove
		Node current = this.first;
		//while current isnt null and isnt pointing to the node to remove,
		//update current to be the next one
		while (current != null && current.next != node){
			current = current.next;
		}
		//if it ran through all the nodes, and didnt find the one to remove, it will become null
		//so we do nothing since it wasnt in the list
		if (current == null){
			return;
		}
		//make the node before the one to remove point to the one the node to remove was pointing to
		current.next = node.next;

		//if the node to remove was the last node, update the previous node to be the last node
		if (node == this.last){
			last = current;
		}
		//decrement size by 1
		size --;
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node current = getNode(index);
		remove (current);
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		int index = indexOf(block);
		if (index == -1){
			throw new IllegalArgumentException(
					"the given memory block is not in this list");
		} else {
			remove(index);
		}
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		String s = "";
		Node current = this.first;
		while (current != null){
			s=s+current.block + " ";
		}
		return s;
	}
}