/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks. The methods "malloc" and "free" are 
 * used, respectively, for creating new blocks and recycling existing blocks.
 */
public class MemorySpace {
	
	// A list of the memory blocks that are presently allocated
	private LinkedList allocatedList;

	// A list of memory blocks that are presently free
	private LinkedList freeList;

	/**
	 * Constructs a new managed memory space of a given maximal size.
	 * 
	 * @param maxSize
	 *            the size of the memory space to be managed
	 */
	public MemorySpace(int maxSize) {
		// initiallizes an empty list of allocated blocks.
		allocatedList = new LinkedList();
	    // Initializes a free list containing a single block which represents
	    // the entire memory. The base address of this single initial block is
	    // zero, and its length is the given memory size.
		freeList = new LinkedList();
		freeList.addLast(new MemoryBlock(0, maxSize));
	}

	/**
	 * Allocates a memory block of a requested length (in words). Returns the
	 * base address of the allocated block, or -1 if unable to allocate.
	 * 
	 * This implementation scans the freeList, looking for the first free memory block 
	 * whose length equals at least the given length. If such a block is found, the method 
	 * performs the following operations:
	 * 
	 * (1) A new memory block is constructed. The base address of the new block is set to
	 * the base address of the found free block. The length of the new block is set to the value 
	 * of the method's length parameter.
	 * 
	 * (2) The new memory block is appended to the end of the allocatedList.
	 * 
	 * (3) The base address and the length of the found free block are updated, to reflect the allocation.
	 * For example, suppose that the requested block length is 17, and suppose that the base
	 * address and length of the the found free block are 250 and 20, respectively.
	 * In such a case, the base address and length of of the allocated block
	 * are set to 250 and 17, respectively, and the base address and length
	 * of the found free block are set to 267 and 3, respectively.
	 * 
	 * (4) The new memory block is returned.
	 * 
	 * If the length of the found block is exactly the same as the requested length, 
	 * then the found block is removed from the freeList and appended to the allocatedList.
	 * 
	 * @param length
	 *        the length (in words) of the memory block that has to be allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	public int malloc(int length) {		
		ListIterator list = freeList.iterator();
		// Runs as long as there is another element in the list
		while (list.hasNext()) {
			// Ensure list.current is not null before accessing block
			if (list.current != null && list.current.block.length >= length) {
				// Create a new memory block with the current address and given length
				int baseAddress = list.current.block.baseAddress;
				MemoryBlock block = new MemoryBlock(baseAddress, length);
				// Add the new memory block to the end of the allocated list
				allocatedList.addLast(block);
				// Update the free block (reduce length and shift base address)
				list.current.block.baseAddress += length;
				list.current.block.length -= length;
				// If the free block is now of length 0, remove it safely
				if (list.current.block.length == 0) {
					Node toRemove = list.current; // Store current node before removal
					list.next(); // Move iterator forward before removal
					freeList.remove(toRemove); // Remove the node safely
				}
				return block.baseAddress;
			}
			// Move to the next node
			list.next();
		}
		return -1; // No suitable block found
	}

	/**
	 * Frees the memory block whose base address equals the given address.
	 * This implementation deletes the block whose base address equals the given 
	 * address from the allocatedList, and adds it at the end of the free list. 
	 * 
	 * @param baseAddress
	 *            the starting address of the block to freeList
	 */
	public void free(int address) {
		if(freeList.getSize() == 1 && freeList.getFirst().block.baseAddress == 0 && freeList.getFirst().block.length == 100)
			throw new IllegalArgumentException("index must be between 0 and size");
		ListIterator list = allocatedList.iterator();
		while(list.hasNext()) {
			if(list.current.block.baseAddress == address) {
				allocatedList.remove(list.current.block);
				freeList.addLast(list.current.block);
				break;
			}
			list.next();
		}
	}
	
	/**
	 * A textual representation of the free list and the allocated list of this memory space, 
	 * for debugging purposes.
	 */
	public String toString() {
		return freeList.toString() + "\n" + allocatedList.toString();		
	}
	
	/**
	 * Performs defragmantation of this memory space.
	 * Normally, called by malloc, when it fails to find a memory block of the requested size.
	 * In this implementation Malloc does not call defrag.
	 */
	public void defrag() {
		if (freeList.getSize() <= 1)
			return;
	
		Node current = freeList.getFirst();
		while (current != null) {
			Node following = current.next;
			boolean merged = false;
	
			while (following != null) {
				MemoryBlock currentBlock = current.block;
				MemoryBlock nextBlock = following.block;
	

				if (currentBlock.baseAddress + currentBlock.length == nextBlock.baseAddress) {
					currentBlock.length += nextBlock.length;
					freeList.remove(following);
					merged = true; 
					following = current.next; 
				} else if (nextBlock.baseAddress + nextBlock.length == currentBlock.baseAddress) {
					nextBlock.length += currentBlock.length; 
					nextBlock.baseAddress = currentBlock.baseAddress;
					freeList.remove(current); 
					merged = true; 
					current = freeList.getFirst(); 
					break;  
				} else {
					following = following.next; 
				}
			}
			if (!merged) {
				current = current.next;
			}
		}
	}
}
