# Data structures 2nd assignment

The goal of this project was to implement **sorting algorithms** as well as our own **priority queue** implementation. We were allowed to use only *our own* data structures implementations, except for arrays, the .io library to read files and Exceptions. It was developed during the 3rd semester as part of the Data Structures course at AUEB.

## Tasks

We were tasked with creating solutions to 3 different problems:

* Given a set of [data](data.txt), [find](Influenza_k.java) the **top k** Cities with the least amount of infections per 50k citizens using **Quicksort**.
  
* [Improve](DynamicInfluenza_k_withPQ.java) the real-time and memory efficiency with the use of a [Priority Queue](PQ.java)

* Given the *same* set of data, [find](Dynamic_Median.java) in real-time the current median.


## How to run each program

### Prerequisites
* Your machine must be able to compile at least java 8

### Common steps
* Download the repository
  
* Fill [data.txt](data.txt) with your data in the format:
`<CityId> <CityName> <CityPopulation> <CityInfections>`

* Open command line on the repository root directory and type: `javac *.java`

### Run solution of 1st problem
`java Influenza_k <Top_k_Cities> data.txt`

### Run solution of 2nd problem
`java DynamicInfluenza_k_withPQ <Top_k_Cities> data.txt`

### Run solution of 3rd problem
`java Dynamic_Median data.txt`

## Contributors
<a href="https://github.com/Morthlog/Data-Structures2/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Morthlog/Data-Structures2"/>
</a>
