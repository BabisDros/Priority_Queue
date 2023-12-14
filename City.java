public class City implements CityInterface, Comparable {
    
    private int id;
    private String name;
    private int population = 1;
    private int infections = 0;
    private float infectRatio = 0f;
    
    /*
     Default constructor
    */
    public City(){}
    
    /**
      @return unique ID of the City
      0 < ID <= 999
    */
    public int getID()
    {
        return id;   
    }
   
    /**
      @return Name of the City
      max of 50 characters
    */
    public String getName()
    {
        return name;   
    }

    /**
      @return Population of the City
      0 < population <= 10.000.000
    */
    public int getPopulation()
    {
        return population;   
    }

    /**
      @return number of Infections in the City
      0 < infections < population
    */    
    public int getInfluenzaCases()
    {
        return infections;   
    }

    /**
      @return number of Infections per 50k citizens
    */    
    public float getInfectRatio()
    {
        return infectRatio;   
    }

    /**
      set the ID of the City
    */    
    public void setID(int ID)
    {
        if (ID > 0 && ID < 1000)
        {
            this.id = ID;
        }
        else 
        {
            terminatePar(ID);
        }
    }

    /**
      set the Name of the City
    */    
    public void setName(String name)
    {
        if (name.length()<=50)
        {
            this.name = name;
        }
        else
        {
            terminatePar(name);
        }
    }

    /**
      set the Population of the City
    */    
    public void setPopulation(int population)
    {
        if (0 < population && population <= 10000000) // less than 10 million
        {
            this.population = population;
        }
        else
        {
            terminatePar(population);
        }
    }

    /**
      set the number of Infections in the City
    */    
    public void setInfluenzaCases(int InfluenzaCases)
    {
        if (0 <= InfluenzaCases && InfluenzaCases <= population)
        {
            this.infections = InfluenzaCases;
        }
        else
        {
            terminatePar(InfluenzaCases);
        }
    }

    /**
     * @return (this < b) ? true : false
     */
//todo ------------------------- TEST -------------------------
    public boolean CompareTo(City b) 
    {
        if (this.getInfectRatio()!=b.getInfectRatio())
        {
            return this.getInfectRatio() < b.getInfectRatio();
        }
        else if (!this.getName().equals(b.getName()))
        {
            int temp = this.getName().compareTo(b.getName());
            if (temp<0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return this.getID() < b.getID();
        }
    }

    /**
     * Finds the number of infections per 50k citizens and saves it to the object
     */
    public void calculateDensity()
    {
        infectRatio = 50000f * (float)getInfluenzaCases() / (float)getPopulation();
        infectRatio = (float)((int)(infectRatio * 100f + 0.5))/100f; // round up
    }

    /*
     * Will immediately stop the program if a parameter is not following the rules
     */
    public void terminatePar(Object problem) // it is either a String or an Int
    {
        try 
        {
            throw new Exception("One of the parameters is not following the specified rules: " + problem);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}


