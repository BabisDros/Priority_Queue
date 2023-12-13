import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DynamicInfluenza_k_withPQ
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int k;
		// * ---------------- Read data from file ---------------- *//
		System.out.println("What is the name of the file with the data? (Do not include the extension .txt)");
		String fileName = input.nextLine();

		// * ---------------- User interaction ---------------- *//
		System.out.println("How many cities should be included in the ranking?");
		k = Integer.parseInt(input.nextLine());
		PQ safestCities = new PQ(2*k, PQ.Type.MIN);
		readData(safestCities, fileName, k);

		if (k > safestCities.size())
		{
			System.out.println("There aren't enough cities to fill the ranks, the program shall exit now");
			System.exit(0);
		}

		// * ---------------- Result ---------------- *//
		System.out.println("The safest "+ k +" cities are:");
		for (int i = 0; i < k; i++)
		{
			System.out.println(safestCities.getHead().getName());
		}

		input.close();
	}

	public static void readData(PQ safestCities, String fileName, int k)
	{
		String line, data = "";
		BufferedReader reader;
		int index;
		City objCity;

		StringDoubleEndedQueue<String> cityCharacteristics = new StringDoubleEndedQueueImpl<String>();
		try
		{
			fileName = fileName + ".txt";
			reader = new BufferedReader(new FileReader(fileName));
			System.out.println("Reading cities' data from file...");
			line = reader.readLine();
			while (line != null)
			{
				objCity = new City();
				while (line != "") // put each word in each own node
				{
					index = line.indexOf(" ");
					if (index >= 0)
					{
						data = line.substring(0, index);
					} else
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
				safestCities.insert(objCity); // add city to heap

				if (safestCities.size() > k)
				{
					safestCities.removeMaxLeaf();
				}
				
				line = reader.readLine();
			}
			
			System.out.println("All data were read succesfully");
			reader.close();
		}

		catch (IOException e)
		{
			System.out.println("Error reading file...\nThe program will now exit");
			System.exit(0);
		}
	}

}
