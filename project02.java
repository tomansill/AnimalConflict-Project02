/* @author Thomas Ansill
 * @course CSCI-331-01
 * @assignment Project 2
 */
import java.util.Scanner;
/** The main class for project2. The main class will be responsible for all input interactions. 
 *  Usage: ./project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]
 *      popSize The size of population in the simulation. The value must be a positive integer
 *      percentHawks The percentage of hawks in the population. The value must be a positive integer and cannot exceed the value of 100.
 *      resourceAmt The amount of resource in the simulation. The value must be a positive integer
 *      costHawk-Hawk The cost to each Hawks in Hawk to Hawk interaction in the simulation. The value must be a positive integer
 *  The program will return 0 if the program ran successfully, otherwise returns 1 if the program did not run successfully.
 */
public class project02{
    /** The main method. It will print error if any. If there are no errors, the simulation will begin and the main method will handle the user's input for simulation menu. 
     *  @param args Commandline arguments
     */
    public static void main(String[] args){
        //Usage
        if(args.length == 0 || args.length > 4){
            System.err.println("Usage: ./project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]");
            System.exit(1);
        }
        int populationSize;
        int percentHawks = 20;
        int resourceAmt = 50;
        int costHawk = 100;
        
        //get population size - required!
        if(!args[0].matches("^[0-9]+$")){
            //Is not an integer
            System.err.println("popSize argument must be a positive integer! Terminating the program.");
            System.exit(1);
        }
        populationSize = Integer.parseInt(args[0]);
        
        //get percentage of Hawks
        if(args.length >= 2){
            if(!args[1].matches("^[0-9]+(\\.[0-9]+)?$")){
                System.err.println("percentHawks argument must be a positive number! Terminating the program.");
                System.exit(1);
            }
            percentHawks = Integer.parseInt(args[1]); //Should truncate the fractional value in a number
            if(percentHawks > 100){
                System.err.println("percentHawks cannot be over 100 percent! Terminating the program.");
                System.exit(1);
            }
        }
        
        //get resource amount
        if(args.length >= 3){
            if(!args[2].matches("^[0-9]+$")){
                System.err.println("resourceAmt argument must be a positive integer! Terminating the program.");
                System.exit(1);
            }
            resourceAmt = Integer.parseInt(args[2]);
        }
        
        //get cost amount
        if(args.length >= 4){
            if(!args[3].matches("^[0-9]+$")){
                System.err.println("costHawk-Hawk argument must be a positive integer! Terminating the program.");
                System.exit(1);
            }
            costHawk = Integer.parseInt(args[3]);
        }
        
        //Begin the simulation
        Simulation sim = new Simulation(populationSize, percentHawks, resourceAmt, costHawk);
        Scanner in = new Scanner(System.in);
        boolean run = true;
        
        //Start the input loop
        while(run){
            //Print the menu
            System.out.println("===============MENU=============");
            System.out.println("1 ) Starting Stats");
            System.out.println("2 ) Display Individuals and Points");
            System.out.println("3 ) Display Sorted");
            System.out.println("4 ) Have 1000 interactions");
            System.out.println("5 ) Have 10000 interactions");
            System.out.println("6 ) Have N interactions");
            System.out.println("7 ) Step through interactions \"Stop\" to quit");
            System.out.println("8 ) Quit");
            System.out.println("================================");
            System.out.print("> ");
            //Gets the user's input
            String input = in.nextLine();
            //Read the input
            if(input.trim().equals("")) System.out.print(""); //Ignore an empty line
            else if(input.matches("^[1-8]?$")){
                switch(Integer.parseInt(input)){
                    //Starting stats
                    case 1:     System.out.println("Population size: " + populationSize);
                                System.out.println("Percentage of Hawks: " + percentHawks + "%");
                                System.out.println("Number of Hawks: " + ((int)(populationSize*(percentHawks/100.0f))));
                                System.out.println("\nPercentage of Doves: " + (100-percentHawks) + "%");
                                System.out.println("Number of Doves: " + ((int)(populationSize*((100-percentHawks)/100.0f))));
                                System.out.println("\nEach resource is worth: " + resourceAmt);
                                System.out.println("Cost of Hawk-Hawk interaction: " + costHawk);
                                break;
                    //Display Individuals and Points
                    case 2:     sim.printIndividuals();
                                break;
                    //Display Sorted
                    case 3:     sim.printSortedIndividuals();
                                break;
                    //Have 1000 interactions
                    case 4:     if(!sim.runSimulation(1000)) System.out.println("There are not enough living individuals in the environment, the simulation cannot continue");
                                break;
                    //Have 10000 interactions
                    case 5:     if(!sim.runSimulation(10000)) System.out.println("There are not enough living individuals in the environment, the simulation cannot continue");
                                break;
                    //Have N interactions
                    case 6:     System.out.print("Number of interactions: ");
                                input = in.nextLine();
                                if(input.matches("^[0-9]+$")){
                                    int interactions = Integer.parseInt(input);
                                    if(!sim.runSimulation(interactions)) System.out.println("There are not enough living individuals in the environment, the simulation cannot continue");
                                }else System.err.println("Number of interactions input is not an positive integer!");
                                break;
                    //Step through interactions \"Stop\" to quit
                    case 7:     boolean steprun = true;
                                while(steprun){
                                    if(!sim.stepSimulation()){
                                        System.out.println("There are not enough living individuals in the environment, the simulation cannot continue");
                                        steprun = false;
                                        break;
                                    }
                                    System.out.println("Type 'stop' to stop the simulation");
                                    System.out.print("> ");
                                    input = in.nextLine();
                                    if(input.equals("stop")) steprun = false;
                                }
                                break;
                    //Quit
                    case 8:     run = false;
                                break;
                    default:    System.err.println("Input not understood. Enter your choice only using integers."); //Probably will never be reached here
                }
            }else System.err.println("Input not understood. Enter your choice only using integers.");
        }
    }
}