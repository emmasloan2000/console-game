/**
 * -
 */
package save.our.planet;

/**
 * 
 */
public class Development {

	private int cost;
	private String title;
	private String description;
	private boolean isMajorDevelopment;
	private Player owner;
	private String name;

	public Development() {

	}

	/**
	 * @param cost
	 * @param title
	 * @param description
	 * @param isMajorDevelopmen
	 */
	public Development(int cost, String title, String description, boolean isMajorDevelopment, Player isBought, String name) {
		super();
		this.cost = cost;
		this.title = title;
		this.description = description;
		this.isMajorDevelopment = isMajorDevelopment;
		this.owner = isBought;
		this.name = name;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isMajorDevelopmen
	 */
	public boolean isMajorDevelopment() {
		return isMajorDevelopment;
	}

	/**
	 * @param isMajorDevelopmen the isMajorDevelopmen to set
	 */
	public void setMajorDevelopment(boolean isMajorDevelopmen) {
		this.isMajorDevelopment = isMajorDevelopmen;
	}

	/**
	 * @return the isBought
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param isBought the isBought to set
	 */
	public void setOwner(Player isBought) {
		this.owner = isBought;
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
