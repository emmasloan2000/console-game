/**
 * -
 */
package save.our.planet;

/**
 * 
 */
public class Square {

	private Field field;
	private int squareNumber;
	private boolean isBlank;
	private boolean isOwned;
	private String name;

	public Square() {

	}

	/**
	 * @param squareType
	 * @param squareNumber
	 * @param isBlank
	 * @param isOwned
	 */
	public Square(Field field, int squareNumber, boolean isBlank, boolean isOwned, String name) {
		super();
		this.field = field;
		this.squareNumber = squareNumber;
		this.isBlank = isBlank;
		this.isOwned = isOwned;
		this.name = name;
	}

	/**
	 * @return the squareNumber
	 */
	public int getSquareNumber() {
		return squareNumber;
	}

	/**
	 * @param squareNumber the squareNumber to set
	 */
	public void setSquareNumber(int squareNumber) {
		this.squareNumber = squareNumber;
	}

	/**
	 * @return the isBlank
	 */
	public boolean isBlank() {
		return isBlank;
	}

	/**
	 * @param isBlank the isBlank to set
	 */
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

	/**
	 * @return the isOwned
	 */
	public boolean isOwned() {
		return isOwned;
	}

	/**
	 * @param isOwned the isOwned to set
	 */
	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}

	/**
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
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
