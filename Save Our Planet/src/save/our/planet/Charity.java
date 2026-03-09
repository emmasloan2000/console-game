/**
 * 
 */
package save.our.planet;

/**
 * 
 */
public class Charity {

	private String icon;
	private int location;

	public Charity() {

	}

	/**
	 * @param icon
	 * @param location
	 */
	public Charity(String icon, int location) {
		super();
		this.icon = icon;
		this.location = location;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
