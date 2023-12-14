import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Influenza_k 
{
    public static void main (String[] args)
    {
        StringDoubleEndedQueue<City> database = new StringDoubleEndedQueueImpl<City>();
        Scanner input = new Scanner(System.in);
        int k = Integer.parseInt(args[0]);
        //* ---------------- Read data from file ---------------- *//
        //?System.out.println("What is the name of the file with the data? (Do not include the extension .txt)");
        //?String name = input.nextLine();
        String name = args[1];
        readData(database, name);
        //* ---------------- User interaction ---------------- *//
        //?System.out.println("How many cities should be included in the ranking?");    It is given from command line
        //?k = Integer.parseInt(input.nextLine());
        if (k>database.size())
        {
            System.out.println("There aren't enough cities to fill the ranks, the program shall exit now...");
            System.exit(0);
        }
        else if (k<0)
        {
            System.out.println("k must be a positive integer, the program shall exit now...");
            System.exit(0);
        }
        //* ---------------- Calculations ---------------- *//
        Node<City> curr = database.getFirst();
        for(int i = 0; i < database.size(); i++){
            curr.getData().calculateDensity();
            curr = curr.getNext();
        }
        //* ---------------- Sorting ---------------- *//
        sorting(database, 0, database.size()-1);
        //* ---------------- Result ---------------- *//
        System.out.println("The top k cities are:");
        for (int i = 0; i < k; i++)
        {
            System.out.println(database.removeFirst().getName());
        }
        input.close();
    }


    public static void readData(StringDoubleEndedQueue<City> database, String name) 
    {
        String line, data = "";
        BufferedReader reader;
        int index;
        City objCity;
        StringDoubleEndedQueue<String> cityCharacteristics = new StringDoubleEndedQueueImpl<String>();
        try
        {
            //?name = name + ".txt";        It is given from command line
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
                database.addLast(objCity); // add city to list
                line = reader.readLine();
            }
            if (database.size() == 0)
            {
                System.out.println("The file was empty, the program will exit now...");
                System.exit(0);
            }
            System.out.println("All data were read succesfully");
            
        }
        catch (IOException e) 
        {
            System.out.println	("Error reading file...\nThe program will now exit");
            System.exit(0);
        }
    }

//? This is a quicksort sorting method

    public static void sorting(StringDoubleEndedQueue<City> queue, int start, int end)
    {
        if (end <= start) 
        {
            return;
        }
        int i = partition(queue, start, end);
        sorting(queue, start, i-1);     
        sorting(queue, i+1, end);  
    }

//! Might need to check if size = 1
    public static int partition(StringDoubleEndedQueue<City> queue, int start, int end)
    {
        Node<City> tempStart, tempEnd, pivot; 
        tempStart = queue.getFirst();
        for (int i=0; i < start; i++) // get the corresponding node based on range [start,end]
        {
            tempStart = tempStart.getNext();
        }
        tempEnd = tempStart;
        for (int i=0; i < end - start; i++)
        {
            tempEnd = tempEnd.getNext();
        }
        pivot = tempEnd;
        int indexStart = start;
        int indexEnd = end;
        while(true)         //start partitioning
        {
            while(tempStart.getData().CompareTo(pivot.getData()))   // finds i and queue[i]
            {
                tempStart = tempStart.getNext();
                indexStart += 1; 
            }
            tempEnd = tempEnd.getPrev();
            indexEnd -= 1;
            while(pivot.getData().CompareTo(tempEnd.getData()))  // finds j and queue[j]
            {
                if (tempEnd == tempStart)
                {
                    break;
                }
                tempEnd = tempEnd.getPrev();
                indexEnd -= 1;
            }
            if (indexStart >= indexEnd)
            {
                break;
            }
            exch(queue, tempStart, tempEnd);
        } 
        exch(queue, tempStart, pivot);
        return indexStart; // i = indexStart
    }


//? Changes the values between nodes (commented code is for debugging purposes)
    
    public static void exch (StringDoubleEndedQueue<City> queue, Node<City> tempStart, Node<City> tempEnd)
    {
        Node<City>  temp = new Node<City>(new City());
        temp.getData().setAll(tempStart);
        tempStart.getData().setAll(tempEnd);
        tempEnd.getData().setAll(temp);
    }
//! result Vienna Amsterdam Athens Thessaloniki Paris



}