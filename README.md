# Data-Structures2
```diff
- Do not use java's data structures, we can use the one we made from previous project
```
## Part A
- [x] Make City Class
- [x] Make CityInterface
- [x] Make Comparable<City> Interface (compareTo)
- [x] Make Influenza_k.java 

**public interface CityInterface** \
  { \
  public int getID(); _check 0 < i <= 999 and unique_ \
  public String getName(); _max 50 characters_ \
  public int getPopulation(); _check 0 < i <= 10.000.000_ \
  public int getInfluenzaCases(); _check 0 < i < Population_ \
  public void setID(int ID); \
  public void setName(String name); \
  public void setPopulation(int population); \
  public void setInfluenzaCases(int InfluenzaCases); \
  }
* if (check is false) {Terminate Program}

* Υλοποιήστε ένα πρόγραμμα το οποίο θα διαβάζει από ένα αρχείο την ημερήσια αναφορά κρουσμάτων, θα ζητάει από τον χρήστη την παράμετρο k, και θα τυπώνει τις πόλεις με τους k μικρότερους αριθμούς κρουσμάτων ανά 50,000 κατοίκους. Το πρόγραμμα θα ονομάζεται Influenza_k.java

**txt format** \
17 [ID] Vienna [Name] 1975000 [Population] 2200 [Infection cases]

* if ( k < number of cities) {Terminate Program}

Sort using `Heapsort` or `Quicksort`

Infections per 50k format: x.xx

**Sorting**: Least infections -> Alphabetically -> ID

* Output: Top k least infected cities


## Part B
- [ ] Make PQ.java

Make priority queue using pile (Dynamic data base?)
* Check `Lab 6` or  `Section 11`
* Generics are allowed

Focus on speed efficiency
  * ![image](https://github.com/Morthlog/Data-Structures2/assets/117933681/e63e91ff-3cfe-48a1-a718-5bf83265d36d) \
    **Check comments**


## Part C

## Part D
