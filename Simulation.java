/* @author Thomas Ansill
 * @course CSCI-331-01
 * @assignment Project 2
 */
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
/** The simulation class. The class creates a population pool and simulates interactions then report on the results */
public class Simulation{
    /** A collection of Individual population */
    private Individual[] population;
    /** A collection of Individuals that are not dead */
    private ArrayList<Individual> alivePool;
    /** The size of the population */
    private int populationSize;
    /** The amount of resource available in the simulation */
    private int resourceAmt;
    /** The variable for cost of hawk to hawk interaction */
    private int costHawk;
    /** The counter of encounters */
    private int numOfEncounters = 0;
    /** Constructor for the Simulation. Once initialized, constructor will build population and shuffle the alive pool.
     *  @param populationSize The size of the population
     *  @param percentHawks The percentage of the hawk in the population
     *  @param resourceAmt The amount of resource available in the simulation
     *  @param costHawk The variable for cost of hawk to hawk interaction
     */
    public Simulation(int populationSize, int percentHawks, int resourceAmt, int costHawk){
        this.populationSize = populationSize;
        this.resourceAmt = resourceAmt;
        this.costHawk = costHawk;
        
        //Build the population pool
        population = new Individual[populationSize];
        alivePool = new ArrayList<Individual>();
        int numOfHawks = (int)(populationSize*(percentHawks/100.0f));
        for(int i = 0; i < populationSize; i++){
            if(i < numOfHawks) population[i] = new Individual(i+1, Species.HAWK);
            else population[i] = new Individual(i+1, Species.DOVE);
            alivePool.add(population[i]);
        }
        Collections.shuffle(alivePool);
    }
    /** The accessor to the count of living Individuals in the simulation
     *  @return The count of living Individuals in the simulation
     */
    public int getAlivePopulationCount(){ return alivePool.size(); }
    /** Prints the list of all Individual in the simulation and their identification number and resource amount */
    public void printIndividuals(){
        for(int i = 0; i < populationSize; i++){
            System.out.print("Individual[" + i + "]=");
            if(population[i].getSpecies() == Species.DOVE) System.out.print("Dove");
            else if(population[i].getResource() < 0) System.out.print("DEAD");
                else System.out.print("Hawk");
            System.out.println(":" + population[i].getResource());
        }
        System.out.println("Living: " + alivePool.size());
    }
    /** Prints the sorted list of all Individual in the simulation and their resource amount */
    public void printSortedIndividuals(){
        LinkedList<Individual> list = new LinkedList<Individual>();
        for(int i = 0; i < populationSize; i++) list.add(population[i]);
        Collections.sort(list, new Comparator<Individual>(){
           public int compare(Individual one, Individual two){
               if(one.getResource() == two.getResource()) return 0;
               else if(one.getResource() < two.getResource()) return 1;
               else return -1;
           } 
        });
        for(Individual individual : list){
            if(individual.getSpecies() == Species.DOVE) System.out.print("Dove");
            else if(individual.getResource() < 0) System.out.print("DEAD");
                else System.out.print("Hawk");
            System.out.println(":" + individual.getResource());
        }
    }
    /** This method will run stepSimulation() method N times. The method will return the appropraite value if the simulation cannot continue
     *  @param numberOfSimulations The number of steps in the simulation
     *  @return Boolean, true if the simulation is successful, false if the simulation cannot continue
     */
    public boolean runSimulation(int numberOfSimulations){
        if(numberOfSimulations < 0)  return true;
        for(int i = 0; i < numberOfSimulations; i++){
            if(getAlivePopulationCount() <= 1) return false; //The simulation cannot continue
            else stepSimulation();
        }
        return true;
    }
    /** This method will process the simulation at one step. The simulation will pick two random alive individuals and make them compete for the resource in the simulation. If both individuals are Doves, they each get half of the resource amount. If both individuals are Hawks, the first selected hawk gets all of the resource amount and both will suffer costs. If hawks' resource amount is below zero, they are dead. If one individual is hawk and another is dove, hawk gets all of the resource and dove gets nothing. The method will return the appropraite value if the simulation cannot continue
     *  @param numberOfSimulations The number of steps in the simulation
     *  @return Boolean, true if the simulation is successful, false if the simulation cannot continue
     */
    public boolean stepSimulation(){
        if(alivePool.size() > 1){
            //Select two random Individuals
            int individual1id = (int)(Math.random() * alivePool.size());
            int individual2id;
            //Repeat until we get two unique numbers (avoid same id numbers)
            while(individual1id == (individual2id = (int)(Math.random() * alivePool.size()))); 
            Individual individual1 = alivePool.get(individual1id);
            Individual individual2 = alivePool.get(individual2id);
            
            //Begin the encounter
            numOfEncounters++;
            System.out.println("Encounter: " + numOfEncounters + "\n" + individual1 + "\n" + individual2);
            if(individual1.getSpecies() == individual2.getSpecies() && individual1.getSpecies() == Species.DOVE){
                //Both Doves, Even resource 
                int resource = resourceAmt/2;
                individual1.addResource(resource);
                individual2.addResource(resource);
                System.out.println("Dove/Dove: Dove: +" + resource + "\tDove: +" + resource);
            }else if(individual1.getSpecies() == individual2.getSpecies() && individual1.getSpecies() == Species.HAWK){
                //Both Hawks, first hawk gets it all, both suffers the hawk-hawk cost
                individual1.addResource(resourceAmt);
                individual1.addResource(-costHawk);
                individual2.addResource(-costHawk);
                System.out.print("Hawk/Hawk: Hawk: ");
                if(resourceAmt - costHawk > 0) System.out.print("+");
                System.out.println((resourceAmt - costHawk) + "\tHawk: -" + costHawk);
                //Check if any of the hawks has died or not (below zero resource)
                if(individual1.getResource() < 0){
                    alivePool.remove(individual1id);
                    System.out.println("Hawk one has died!");
                    //adjust individual2's id 
                    if(individual1id < individual2id) individual2id--;
                }
                if(individual2.getResource() < 0){
                    alivePool.remove(individual2id);
                    System.out.println("Hawk two has died!");
                }
            }else{
                //Different species
                if(individual1.getSpecies() == Species.HAWK) individual1.addResource(resourceAmt);
                else individual2.addResource(resourceAmt);
                System.out.println("Hawk/Dove: Hawk: +" + resourceAmt + "\tDove: +0");
            }
            System.out.print("Individual " + individual1.getId() + "=" + individual1.getResource());
            System.out.println("\tIndividual " + individual2.getId() + "=" + individual2.getResource());
	    System.out.println();
            return true;
        }else return false;
    }
}
