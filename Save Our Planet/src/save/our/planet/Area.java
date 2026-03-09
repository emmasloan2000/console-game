
/**
* -
*/
package save.our.planet;
 
import java.util.ArrayList;
 
/**
*
*/
public class Area extends Square {
	/**
	 * instance variables 
	 */
	private int cost;
	private Player owner;
	private ArrayList<Development> developments;
	private int minorDevOwnedCounter;
	private int majorDevOwnedCounter;
 
	/**
	 * default constructor 
	 */
	public Area() {
 
	}
 
	/**
	* @param cost
	* @param owner
	* @param developments
	* @param minorDevOwnedCounter
	* @param majorDevOwnedCounter
	*/
	public Area(int cost, Player owner, ArrayList<Development> developments, int minorDevOwnedCounter,
			int majorDevOwnedCounter) {
		super();
		this.cost = cost;
		this.owner = owner;
		this.developments = developments;
		this.minorDevOwnedCounter = minorDevOwnedCounter;
		this.majorDevOwnedCounter = majorDevOwnedCounter;
	}
 
	/**
	* @return the cost
	*/
	public int getCost() {
		return cost;
	}
 
	/**
	* @param cost the cost to set
	*/
	public void setCost(int cost) {
		this.cost = cost;
	}
 
	/**
	* @return the owner
	*/
	public Player getOwner() {
		return owner;
	}
 
	/**
	* @param owner the owner to set
	*/
	public void setOwner(Player owner) {
		this.owner = owner;
	}
 
	/**
	* @return the developments
	*/
	public ArrayList<Development> getDevelopments() {
		return developments;
	}
 
	/**
	* @param developments the developments to set
	*/
	public void setDevelopments(ArrayList<Development> developments) {
		this.developments = developments;
	}
 
	/**
	* @return the minorDevOwnedCounter
	*/
	public int getMinorDevOwnedCounter() {
		return minorDevOwnedCounter;
	}
 
	/**
	* @param minorDevOwnedCounter the minorDevOwnedCounter to set
	*/
	public void setMinorDevOwnedCounter(int minorDevOwnedCounter) {
		this.minorDevOwnedCounter = minorDevOwnedCounter;
	}
 
	/**
	* @return the majorDevOwnedCounter
	*/
	public int getMajorDevOwnedCounter() {
		return majorDevOwnedCounter;
	}
 
	/**
	* @param majorDevOwnedCounter the majorDevOwnedCounter to set
	*/
	public void setMajorDevOwnedCounter(int majorDevOwnedCounter) {
		this.majorDevOwnedCounter = majorDevOwnedCounter;
	}

	
}
	


