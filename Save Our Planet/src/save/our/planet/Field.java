/**
 * -
 */
package save.our.planet;

import java.util.ArrayList;

/**
 * 
 */
public class Field {

	private Player owner;
	private ArrayList<Area> areas;
	private int areasOwnedCounter;
	private int majorDevelopmentsCounter;
	private String name;

	public Field() {

	}

	/**
	 * @param owner
	 * @param areas
	 * @param areasOwnedCounter
	 * @param majorDevelopmentsCounter
	 */
	public Field(Player owner, ArrayList<Area> areas, int areasOwnedCounter, int majorDevelopmentsCounter, String name) {
		super();
		this.owner = owner;
		this.areas = areas;
		this.areasOwnedCounter = areasOwnedCounter;
		this.majorDevelopmentsCounter = majorDevelopmentsCounter;
		this.name = name;
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
	 * @return the areas
	 */
	public ArrayList<Area> getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}

	/**
	 * @return the areasOwnedCounter
	 */
	public int getAreasOwnedCounter() {
		return areasOwnedCounter;
	}

	/**
	 * @param areasOwnedCounter the areasOwnedCounter to set
	 */
	public void setAreasOwnedCounter(int areasOwnedCounter) {
		this.areasOwnedCounter = areasOwnedCounter;
	}

	/**
	 * @return the majorDevelopmentsCounter
	 */
	public int getMajorDevelopmentsCounter() {
		return majorDevelopmentsCounter;
	}

	/**
	 * @param majorDevelopmentsCounter the majorDevelopmentsCounter to set
	 */
	public void setMajorDevelopmentsCounter(int majorDevelopmentsCounter) {
		this.majorDevelopmentsCounter = majorDevelopmentsCounter;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
