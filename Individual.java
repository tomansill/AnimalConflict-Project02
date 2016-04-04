/* @author Thomas Ansill
 * @course CSCI-331-01
 * @assignment Project 2
 */
/** Individual class
 *  The individual interacts the simulation world and holds a number of resource. The individual can be either Dove or Hawk
 */
public class Individual{
    /** Individual's species assignment */
    private Species species;
    /** Individual's resource amount */
    private int resource;
    /** Individual's assigned identification number */
    private int id;
    /** Constructor for Individual
     *  @param id Assigned Identification number
     *  @param species Assigned species
     */
    public Individual(int id, Species species){
        this.id = id;
        this.species = species;
    }
    /** Accessor to this Individual's species assignment
     *  @return Individual's species assignment
     */
    public Species getSpecies(){ return species; }
    /** Accessor to this Individual's resource amount
     *  @return Individual's resource amount
     */
    public int getResource(){ return resource; }
    /** Accessor to this Individual's assigned identification number
     *  @return Individual's identification number
     */
    public int getId(){ return id; }
    /** Mutator to this Individual's resource amount
     *  @param resource Amount of resource to be added into this Individual's resource amount. Positive and Negative integer is allowed.
     */
    public void addResource(int resource){ this.resource += resource; }
    /** The toString method for Individual */
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Individual ");
        str.append(id);
        str.append(": ");
        if(species == Species.DOVE) str.append("Dove");
        if(species == Species.HAWK) str.append("Hawk");
        //str.append(" " + resource); //Useful debugging information
        return str.toString();
    }
}