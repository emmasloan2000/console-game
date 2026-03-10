/**
 * -
 */
package save.our.planet;

/**
 * 
 */
public class Charity {

	private CharitySymbol symbol;
	private int location;

	public Charity() {

	}

	/**
	 * @param icon
	 * @param location
	 */
	public Charity(CharitySymbol icon, int location) {
		super();
		this.symbol = icon;
		this.location = location;
	}

	/**
	 * @return the icon
	 */
	public CharitySymbol getIcon() {
		return symbol;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(CharitySymbol icon) {
		this.symbol = icon;
	}

	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(int location) {
		this.location = location;
	}

}
