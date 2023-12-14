
public class PQ
{
	private City[] heap; // the heap to store data in
	private int size; // current size of the queue
	private int[] idHeapPos = new int[1000];// to store the position of the id in the heap with id as index. eg
											// idHeapPos[id]=position in heap
	private static final int AUTOGROW_COEF = 2; // default auto grow
	private static final int DEFAULT_SIZE = 4;
	private Type currentType;

	public enum Type
	{
		MIN, MAX
	}

	/**
	 * Queue constructor
	 *
	 */
	public PQ(int capacity, Type type)
	{
		currentType = type;
		this.heap = new City[capacity + 1];
		this.size = 0;
	}

	public PQ(Type type)
	{
		this(DEFAULT_SIZE, type);
	}

	boolean isEmpty()
	{
		return size == 0;
	}

	int size()
	{
		return size;
	}

	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @param city
	 */
	public void insert(City city)
	{ // System.out.println((int) item);
		// Check available space
		if (size == Math.round(0.75 * (heap.length - 1)))
			grow();

		// Place item at the next available position

		heap[++size] = city;

		// create a reference of the position in helper array. Will be updated in if
		// swapped.
		idHeapPos[city.getID()] = size;

		// Let the newly added item swim
		swim(size);
	}

	/*
	 * We could create max, min that wοuld get data from peek and getMax, getMin
	 * that would get data from getHead according to the type of heap, but it is
	 * preferred to use generic names in order to avoid creating multiple methods.
	 * 
	 */
	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null if
	 * this queue is empty.
	 *
	 * @return the head of the queue
	 * Used to be public City min()
	 */ 
	public City peek()
	{
		// Ensure not empty
		if (isEmpty())
			return null;

		// return root without removing
		return heap[1];
	}

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue
	 * is empty.
	 *
	 * @return the head of the queue
	 * Used to be public City getMin()
	 */
	public City getHead()
	{
		// Ensure not empty
		if (isEmpty())
			return null;

		// Keep a reference to the root item
		City root = heap[1];

		// Replace root item with the one at rightmost leaf
		heap[1] = heap[size];

		// Dispose the rightmost leaf
		size--;

		// create a reference of the position in helper array. Will be updated in if
		// swapped.
		idHeapPos[heap[1].getID()] = 1;

		// Sink the new root element
		sink(1);
		// Return the int removed
		return root;
	}

	public City remove(int id)
	{
		// Ensure not empty
		if (id < 0 || id > idHeapPos.length - 1 || isEmpty())
			return null;
		// get the position of the id in the heap, from the helper array
		int idx = idHeapPos[id];

		// if id not found, return null
		if (idx == -1)
			return null;

		// remove the reference to the position of the id from the helper array
		idHeapPos[id] = -1;
		City removed = heap[idx];

		// if the index removed is the not the rightmost leaf, move the rightmost leaf to
		// the removed position
		if (idx != size)
		{
			heap[idx] = heap[size];

			// update the new position of the moved rightmost leaf, to the helper class.Will
			// be updated if there is a swap.
			idHeapPos[heap[idx].getID()] = idx;
		}
		size--;

		// if the removed index is a leaf swim
		if (idx >= getFirstLeafIndex())
		{
			swim(idx);
		}

		else
		{
			sink(idx);
		}

		return removed;
	}

	// from discrete math: leaves = (noOfNodes + 1)/2
	int totalLeaves()
	{
		return (size + 1) / 2;
	}

	int getFirstLeafIndex()
	{
		return size - totalLeaves() + 1;
	}

	/**
	 * Retrieves and removes the max leaf of queue when it is a Min-Type ONLY.
	 * Returns null if this queue is empty or Max-Type.
	 *
	 * @return the max
	 */
	public City removeMaxLeaf()
	{
		if (isEmpty() || currentType == Type.MAX)
			return null;

		float max = heap[size].getInfectRatio();
		int index = size;

		int beforeLastLeaf = size - 1;
		int firstLeafIdx = getFirstLeafIndex();
		// compare with the other leaves and find max
		for (int i = beforeLastLeaf; i >= firstLeafIdx; i--)
		{
			float currentInfectRatio = heap[i].getInfectRatio();

			if (currentInfectRatio > max)
			{
				max = currentInfectRatio;
				index = i;
			}
		}
		return remove(heap[index].getID());
	}

	/**
	 * Helper function to swim cities to the top
	 *
	 * @param i the index of the city to swim
	 */
	private void swim(int i)
	{
		// if i is root (i==1) return
		if (i == 1)
			return;

		// find parent
		int parent = i / 2;

		// compare parent with child i
		while (i != 1 && (currentType == Type.MIN && heap[i].CompareTo(heap[parent])
				|| (currentType == Type.MAX && heap[parent].CompareTo(heap[i]))))
		{
			swap(i, parent);
			i = parent;
			parent = i / 2;

		}
		return;
	}

	/**
	 * Helper function to sink cities to the bottom
	 *
	 * @param i the index of the city to sink
	 */
	private void sink(int i)
	{
		// determine left, right child
		int left = 2 * i;
		int right = left + 1;

		// if 2*i > size, node i is a leaf return
		if (left > size)
			return;

		// while haven't reached the leafs
		while (left <= size)
		{
			int min = left;
			int max = left;
			int currentChild = left;
			if (right <= size)
			{
				if ((currentType == Type.MIN && heap[right].CompareTo(heap[left]))
						|| (currentType == Type.MAX && heap[left].CompareTo(heap[right])))
				{
					min = right;
					max = right;
					currentChild = right;
				}
			}

			// If the heap condition holds, stop. Else swap and go on.
			// heap[i] is parent
			if ((currentType == Type.MIN && heap[i].CompareTo(heap[min]))
					|| (currentType == Type.MAX && heap[max].CompareTo(heap[i])))
				return;
			else
			{
				swap(i, currentChild);
				i = currentChild;
				left = i * 2;
				right = left + 1;
			}
		}
		return;
	}

	/**
	 * Helper function to swap two elements in the heap and update the position in
	 * the auxiliary idHeapPos
	 *
	 * @param i the first element's index
	 * @param j the second element's index
	 */
	private void swap(int i, int j)
	{
		// update the stored position of the id
		idHeapPos[heap[i].getID()] = j;
		idHeapPos[heap[j].getID()] = i;

		City tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

	/**
	 * Helper function to grow the size of the heap
	 */
	private void grow()
	{
		City[] newHeap = new City[heap.length * AUTOGROW_COEF];

		// copy array
		// (notice: in the priority queue, elements are located in the array slots with
		// positions in [1, size])
		for (int i = 0; i <= size; i++)
		{
			newHeap[i] = heap[i];
		}

		heap = newHeap;
	}
}
