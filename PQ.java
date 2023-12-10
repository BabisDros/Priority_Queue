import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PQ <T>
{
	private T[] heap; // the heap to store data in
    private int size; // current size of the queue
    private Comparator<T> comparator; // the comparator to use between the objects
    private int[] idHeapPos=new int[1000];//to store the position of id
    private static final int DEFAULT_CAPACITY = 5; // default capacity
    private static final int AUTOGROW_COEF = 2; // default auto grow

    /**
     * Queue constructor
     *
     * @param comparator
     */
    public PQ(Comparator<T> comparator) 
    {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
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
     * @param item
     */
    public void insert(T item) 
    { 	 //System.out.println((int) item);
        // Check available space
        if (size ==Math.round(0.75*(heap.length - 1))) 
            grow();

        // Place item at the next available position
        
        heap[++size] = item;

        // Let the newly added item swim
        idHeapPos[(int) item]=size;
        swim(size);
    }

    
    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    public T min() 
    {
        // Ensure not empty
        if (isEmpty())
            return null;

        // return root without removing
        return heap[1];
    }
    
    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    public T getMin() 
    {
        // Ensure not empty
        if (isEmpty())
            return null;

        // Keep a reference to the root item
        T root = heap[1];

        // Replace root item with the one at rightmost leaf
        heap[1] = heap[size];
       
        size--;

        // Dispose the rightmost leaf
        // Sink the new root element
        idHeapPos[(int)heap[1]]=1;//temporar position
        sink(1);
        // Return the int removed
        return root;
    }
    
    public T remove(int id) 
    {
    	// Ensure not empty
        if (isEmpty())
            return null;
        
        int idx=idHeapPos[id];
        T removed= heap[idx];
        heap[idx]= heap[size];
        size--;
        
        idHeapPos[(int) heap[idx]] = idx;
        sink(idx);
        
		return removed;    	
    }



    /**
     * Helper function to swim items to the top
     *
     * @param i the index of the item to swim
     */
    private void swim(int i) 
    {
        // if i is root (i==1) return
        if (i == 1)
            return;

        // find parent
        int parent = i / 2;

        // compare parent with child i
        while (i != 1 && comparator.compare(heap[i], heap[parent]) < 0) 
        {
            swap(i, parent);
            i = parent;
            parent = i / 2;
            
        }
        return;
    }

    /**
     * Helper function to sink items to the bottom
     *
     * @param i the index of the item to sink
     */
    private void sink(int i) 
    {
        // determine left, right child
        int left = 2 * i;
        int right = left + 1;

        // if 2*i > size, node i is a leaf return
        if (left > size)
        	return ;

        // while haven't reached the leafs
        while (left <= size) 
        {
            // Determine the smallest child of node i
            int min = left;
            if (right <= size) 
            {
                if (comparator.compare(heap[left], heap[right]) < 0)
                    min = left;
            }

            // If the heap condition holds, stop. Else swap and go on.
            // child smaller than parent
            if (comparator.compare(heap[i], heap[min]) < 0)
            	return;
            else 
            {
                swap(i, min);
                i = min;
                left = i * 2;
                right = left + 1;
            }
           
        }
		return;
    }

    /**
     * Helper function to swap two elements in the heap
     *
     * @param i the first element's index
     * @param j the second element's index
     */
    private void swap(int i, int j) 
    {
    	//update the stored position of the id
    	idHeapPos[(int) heap[i]]=j;
    	idHeapPos[(int) heap[j]]=i;
         
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Helper function to grow the size of the heap
     */
    private void grow() 
    {
        T[] newHeap = (T[]) new Object[heap.length * AUTOGROW_COEF];

        // copy array
		//(notice: in the priority queue, elements are located in the array slots with positions in [1, size])
        for (int i = 0; i <= size; i++) 
        {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }
    int elementForDebug=0;
    public static void main(String[] args) 
    {        
        int heapSize = 10;
        PQ<Integer> minPriorityQueue = new PQ<>(new IntegerComparator());
//        minPriorityQueue = generateRandomHeap(heapSize);
        // Adding 10 elements to the heap
        for (int i = 1; i <= 999; i++) 
        {
        	minPriorityQueue.insert(i);
        }
        // Print the randomly generated heap
        System.out.println("Initial Heap:");
        minPriorityQueue.printHeapTree();
        
        minPriorityQueue.isAmismatch();
        //minPriorityQueue.insert(300);

//      Testing min method
        System.out.println("Min element: " + minPriorityQueue.min());
        
//      Testing getMin method
        System.out.println("Removed min element: " + minPriorityQueue.getMin());
        minPriorityQueue.printHeapTree();
        minPriorityQueue.isAmismatch();
        
//      Testing min method
        System.out.println("Min element: " + minPriorityQueue.min());
//      Testing remove method
        System.out.println("Removed element 5: " + minPriorityQueue.remove(5));
        minPriorityQueue.printHeapTree();
        minPriorityQueue.isAmismatch();

//      Displaying the size of the priority queue
        System.out.println("Size of priority queue: " + minPriorityQueue.size());        
    }

    
    public static PQ<Integer> generateRandomHeap(int heapSize) 
    {
    	 Random random = new Random();
    	 Set<Integer> uniqueNumbers = new HashSet<>();//for uniqueness
         PQ<Integer> randomHeap = new PQ<>(new IntegerComparator());

         while (uniqueNumbers.size() < heapSize) 
         {
             int randomNumber = random.nextInt(100)+1; // Adjust the range as needed
             if (uniqueNumbers.add(randomNumber)) 
             {
                 randomHeap.insert(randomNumber);
             }
         }

         return randomHeap;
    }
    public void printHeapTree() 
    {
        int elementsInLevel = 1;
        
        for (int i = 1; i <= size; i++) 
        {
            System.out.print(heap[i] + " ");
            
            if (i == elementsInLevel) 
            {
                System.out.println(); // Move to the next level
                elementsInLevel = elementsInLevel * 2+1;
            }
        }
        System.out.println();
    }
    
    public boolean isAmismatch() 
    {
    	for (int i=0;i<size;i++) 
    	{
			if(heap[i]!=null && i!=idHeapPos[(int) heap[i]])
			{
				System.out.println("there IS a mismatch in position"+heap[i]);
				return true;
			}
		
		}
    	System.out.println("there is NOT a mismatch");
    	return false;
    }
}



