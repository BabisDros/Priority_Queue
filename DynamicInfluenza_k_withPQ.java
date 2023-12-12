import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DynamicInfluenza_k_withPQ
{
	public static void main (String[] args)
    {
		
        Scanner input = new Scanner(System.in);
        int k;
        //* ---------------- Read data from file ---------------- *//
        System.out.println("What is the name of the file with the data? (Do not include the extension .txt)");
        String name = input.nextLine();
       
        //* ---------------- User interaction ---------------- *//
        System.out.println("How many cities should be included in the ranking?");
        k = Integer.parseInt(input.nextLine());
        PQ database=new PQ(k,PQ.Type.MIN);
        readData(database, name);
        
        if (k>database.size())
        {
            System.out.println("There aren't enough cities to fill the ranks, the program shall exit now");
            System.exit(0);
        }
        //* ---------------- Calculations ---------------- *//
        City current = database.getHead();
        for(int i = 0; i < database.size(); i++)
        {
        	current.calculateDensity();
        }
   
        //* ---------------- Result ---------------- *//
        System.out.println("The top k cities are:");
        for (int i = 0; i < k; i++)
        {
            System.out.println(database.getHead().getName());
        }
        
        input.close();
    }
	
	public static void readData(PQ database, String name) 
    {
        String line, data = "";
        BufferedReader reader;
        int index;
        City objCity;
        int lineCounter=0;
        
        StringDoubleEndedQueue<String> cityCharacteristics = new StringDoubleEndedQueueImpl<String>();
        try
        {
            name = name + ".txt";
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
               
                 
                 database.insert(objCity); // add city to heap
                 line = reader.readLine();
                 
                 
                lineCounter++;
                if(lineCounter>database.size())
                {                	
                	database.getHead();
                }
                
                if(lineCounter%5==0)
                {
                	for(int i=1;i<=5;i++)
                	{
                	
                		System.out.println(database.getHead().getName());
                	}
                	
                }
            }
            System.out.println("All data were read succesfully");
        }
        
        catch (IOException e) 
        {
            System.out.println	("Error reading file...\nThe program will now exit");
            System.exit(0);
        }
    }
}
