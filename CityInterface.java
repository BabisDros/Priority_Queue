public interface CityInterface {

    /**
      @return unique ID of the City
    */
    public int getID();
   
    /**
      @return Name of the City
    */
    public String getName();

    /**
      @return Population of the City
    */
    public int getPopulation();

    /**
      @return number of Infections in the City
    */    
    public int getInfluenzaCases();

    /**
      set the ID of the City
    */    
    public void setID(int ID) throws Exception;

    /**
      set the Name of the City
    */    
    public void setName(String name);

    /**
      set the Population of the City
    */    
    public void setPopulation(int population);

    /**
      set the number of Infections in the City
    */    
    public void setInfluenzaCases(int InfluenzaCases);
}
