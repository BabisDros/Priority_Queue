
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PQ
{
	private City[] heap; // the heap to store data in
    private int size; // current size of the queue
    private int[] idHeapPos=new int[1000];//to store the position of id
    private static final int DEFAULT_CAPACITY = 5; // default capacity
    private static final int AUTOGROW_COEF = 2; // default auto grow

    /**
     * Queue constructor
     *
     * @param comparator
     */
    public PQ() 
    {
        this.heap = new City[DEFAULT_CAPACITY + 1];
        this.size = 0;
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
    public void insert(City item) 
    { 	 //System.out.println((int) item);
        // Check available space
        if (size ==Math.round(0.75*(heap.length - 1))) 
            grow();

        // Place item at the next available position
        
        heap[++size] = item;

        // Let the newly added item swim
        idHeapPos[item.getID()]=size;
        swim(size);
    }

    
    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    public City min() 
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
    public City getMin() 
    {
        // Ensure not empty
        if (isEmpty())
            return null;

        // Keep a reference to the root item
        City root = heap[1];

        // Replace root item with the one at rightmost leaf
        heap[1] = heap[size];
       
        size--;

        // Dispose the rightmost leaf
        // Sink the new root element
        idHeapPos[heap[1].getID()]=1;//temporar position
        sink(1);
        // Return the int removed
        return root;
    }
    
    public City remove(int id) 
    {
    	// Ensure not empty
        if (isEmpty())
            return null;
        
        int idx=idHeapPos[id];
        City removed= heap[idx];
        heap[idx]= heap[size];
        size--;
        
        idHeapPos[heap[idx].getID()] = idx;
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
        while (i != 1 && (heap[i].CompareTo(heap[parent])))        		 
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
                if (heap[left].CompareTo(heap[right]))
                    min = left;
            }

            // If the heap condition holds, stop. Else swap and go on.
            // child smaller than parent
            if (heap[i].CompareTo(heap[min]))
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
    	idHeapPos[heap[i].getID()]=j;
    	idHeapPos[heap[j].getID()]=i;
         
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
		//(notice: in the priority queue, elements are located in the array slots with positions in [1, size])
        for (int i = 0; i <= size; i++) 
        {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }
    
    /*----------helper classes for debug------------*/
    
    public static void main(String[] args) 
    {        
    	 PQ minPriorityQueue = new PQ();
        Set<Integer> uniqueIDs = new HashSet<>();
        for (int i = 0; i < 10; i++) 
        {
            City city = CityGenerator.generateCity();
            
            // Ensure unique IDs
            if (uniqueIDs.contains(city.getID())) {
                System.out.println("Duplicate ID generated: " + city.getID());
            }

            uniqueIDs.add(city.getID());

            // Print city details
            System.out.println("City " + (i + 1) + ":");
            System.out.println("ID: " + city.getID());
            System.out.println("Name: " + city.getName());
            System.out.println("Population: " + city.getPopulation());
            System.out.println("Influenza Cases: " + city.getInfluenzaCases());
            System.out.println();
            minPriorityQueue.insert(city);
        }
        // Print the randomly generated heap
        System.out.println("Initial Heap:");
       
		minPriorityQueue.printHeapTree();
        
        minPriorityQueue.isAmismatch();
        //minPriorityQueue.insert(300);

//      Testing min method
        System.out.println("Min element: " + minPriorityQueue.min().getInfectRatio());
        
//      Testing getMin method
        System.out.println("Removed min element: " + minPriorityQueue.getMin().getInfectRatio());
        minPriorityQueue.printHeapTree();
        minPriorityQueue.isAmismatch();
        
//      Testing min method
        System.out.println("Min element: " + minPriorityQueue.min().getInfectRatio());
//      Testing remove method
        System.out.println("Removed element: " + minPriorityQueue.remove(9).getInfectRatio());
        minPriorityQueue.printHeapTree();
        minPriorityQueue.isAmismatch();

//      Displaying the size of the priority queue
        System.out.println("Size of priority queue: " + minPriorityQueue.size());        
    }

    
    
    
    public void printHeapTree() 
    {
        int elementsInLevel = 1;
        
        for (int i = 1; i <= size; i++) 
        {
            System.out.print(heap[i].getInfectRatio() + " ");
            
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
			if(heap[i]!=null && i!=idHeapPos[heap[i].getID()])
			{
				System.out.println("there IS a mismatch in position"+heap[i]);
				return true;
			}
		
		}
    	System.out.println("there is NOT a mismatch");
    	return false;
    }
    
    public class CityGenerator 
    {
        private static int cityCount = 0;

        public static City generateCity() {
            City city = new City();

            try {
                city.setID(cityCount + 1); // Unique ID
                city.setName(generateCityName());
                city.setPopulation((cityCount + 1) * 1000); // Example population based on city count
                city.setInfluenzaCases(cityCount % 100); // Example influenza cases based on city count
                city.calculateDensity();
                cityCount++;
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            return city;
        }

        private static String generateCityName() {
            return "City" + (cityCount + 1);
        }

        public static void main(String[] args) {
            City generatedCity = generateCity();
            System.out.println(generatedCity);
        }
    }

    public class RandomCityGenerator 
    {
    	public static City generateCity() 
        {
            City city = new City();
            Random random = new Random();

            try 
            {
                city.setID(random.nextInt(1000) + 1); // Unique ID
                city.setName("City" + city.getID());
                city.setPopulation(random.nextInt(1000000) + 1000);
                city.setInfluenzaCases(random.nextInt(100));
                city.calculateDensity();
            } 
            catch (Exception e) 
            {
                e.printStackTrace(); // Handle the exception appropriately
            }

            return city;
        }
    }
	
}



