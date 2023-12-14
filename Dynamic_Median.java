import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Dynamic_Median {

    public static void main (String[] args)
    {
        PQ minHeap = new PQ(PQ.Type.MIN);
        PQ maxHeap = new PQ(PQ.Type.MAX);
        Scanner input = new Scanner(System.in);
        //* ---------------- Read data from file ---------------- *//
        //? System.out.println("What is the name of the file with the data? (Do not include the extension .txt)");
        //? String name = input.nextLine();     Given at command line
        String name = args[0];
        readData(minHeap, maxHeap, name);

        input.close();
    }




public static void readData(PQ minHeap, PQ maxHeap, String name) 
    {
        String line, data = "";
        BufferedReader reader;
        int index;
        City objCity, median = null;
        StringDoubleEndedQueue<String> cityCharacteristics = new StringDoubleEndedQueueImpl<String>();
        try
        {
            //?name = name + ".txt";      it is given already
            reader = new BufferedReader(new FileReader(name));
            System.out.println("Reading cities' data from file...");
            line = reader.readLine();
            while (line != null)
            {
                objCity = new City();
                while (line != "")  // put each word in each own node
                {
                    index = line.indexOf(" ");
                    if (index >= 0)
                    {
                        data = line.substring(0, index);
                    }
                    else
                    {
                        data = line;
                        index = line.length();
                    }
                        cityCharacteristics.addLast(data);
                        line = line.substring(index).trim();
                }
                objCity.setID(Integer.parseInt(cityCharacteristics.removeFirst()));
                objCity.setName(cityCharacteristics.removeFirst());
                objCity.setPopulation(Integer.parseInt(cityCharacteristics.removeFirst()));
                objCity.setInfluenzaCases(Integer.parseInt(cityCharacteristics.removeFirst()));
                objCity.calculateDensity();
                median = calculate(minHeap, maxHeap, objCity, median); // add city to heap and find median
                if ((minHeap.size() + maxHeap.size())%5==0)
                {
                    output(median);
                }
                line = reader.readLine();
            }
            System.out.println("All data were read succesfully");
        }
        catch (IOException e) 
        {
            System.out.println	("Error reading file...\nThe program will now exit");
            System.exit(0);
        }
    }

    /**
     *@print the current median 
     */
    static void output(City median)
    {
        System.out.printf("The current median is on %s with %.2f infections per 50k citizens\n", median.getName(), median.getInfectRatio());
    }

    /**
     *@calculate the current median 
     */
    static City calculate(PQ minHeap, PQ maxHeap, City newCity, City median)
    {
        if (median == null)
        {
            minHeap.insert(newCity);
            return newCity;
        }
        
        if (median.CompareTo(newCity))  // only kept when we end with odd amount of objects
        {
            minHeap.insert(newCity);
            median = minHeap.peek();
        }
        else
        {
            maxHeap.insert(newCity);
            median = maxHeap.peek();
        }

        
        if (minHeap.size()>maxHeap.size()+1) // check if one heap has at least 2 more elements than the other
        {
            maxHeap.insert(minHeap.getHead());
        }
        else if (maxHeap.size()>minHeap.size()+1)
        {
            minHeap.insert(maxHeap.getHead());
        }

        if (minHeap.size()==maxHeap.size()) // equal distribution, so we have even amount of objects, meaning we get the bottom half
        {
            if (minHeap.peek().CompareTo(maxHeap.peek()))
            {
                median = minHeap.peek();
            }
            else
            {
                median = maxHeap.peek();
            }
        }
        return median;
    }
}
