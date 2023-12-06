import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Influenza_k 
{
    public static void main (String[] args)
    {
        StringDoubleEndedQueue<City> database = new StringDoubleEndedQueueImpl<City>();
        StringDoubleEndedQueue<String> cityCharacteristics = new StringDoubleEndedQueueImpl<String>();
        Scanner input = new Scanner(System.in);
        int k;
        //* ---------------- Read data from file ---------------- *//
        
        database = readData();
        System.out.println("How many cities should be included in the ranking?");
        k = Integer.parseInt(input.nextLine());
        if (k>database.size())
        {
            System.out.println("There aren't enough cities to fill the ranks, the program shall exit now");
            System.exit(0);
        }
    }


    public static StringDoubleEndedQueue<City> readData() 
    {
        String line, data = "";
        BufferedReader reader;
        int index;
        City objCity;
        StringDoubleEndedQueue<City> database = new StringDoubleEndedQueueImpl<City>();
        StringDoubleEndedQueue<String> cityCharacteristics = new StringDoubleEndedQueueImpl<String>();
        try
        {
            reader = new BufferedReader(new FileReader("data.txt"));
            System.out.println("Reading cities' data from file...");
            line = reader.readLine();
            while (line != null)
            {
                objCity = new City();
                while (line != "")
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
                database.addLast(objCity);
                line = reader.readLine();
            }
            System.out.println("All data were read succesfully");
        }
        catch (IOException e) 
        {
            System.out.println	("Error reading file...");
        }
        return database;
    }
}
