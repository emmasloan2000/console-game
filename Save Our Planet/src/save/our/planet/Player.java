/**
 * -
 */
package save.our.planet;

import java.util.ArrayList;

/**
 * 
 */
public class Player {

	private String name;
	private Charity charity;
	private ArrayList<Area> ownedAreas;
	private ArrayList<Development> developments;
	private int currency;
	private int position;

	public Player() {

	}

	/**
	 * @param name
	 * @param charity
	 * @param ownedAreas
	 * @param developments
	 * @param currency
	 */
	public Player(String name, Charity charity, ArrayList<Area> ownedAreas, ArrayList<Development> developments,
			int currency) {
		super();
		this.name = name;
		this.charity = charity;
		this.ownedAreas = ownedAreas;
		this.developments = developments;
		this.currency = currency;
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

	/**
	 * @return the charity
	 */
	public Charity getCharity() {
		return charity;
	}

	/**
	 * @param charityName the charity to set
	 */
	public void setCharity(Charity charity) {
		this.charity = charity;
	}

	/**
	 * @return the ownedAreas
	 */
	public ArrayList<Area> getOwnedAreas() {
		return ownedAreas;
	}

	/**
	 * @param ownedAreas the ownedAreas to set
	 */
	public void setOwnedAreas(ArrayList<Area> ownedAreas) {
		this.ownedAreas = ownedAreas;
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
	 * @return the currency
	 */
	public int getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	/**
	 * getters for position
	 * @return
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * setters for position
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	public boolean hasFunds(Area currentSquare) {
		if (this.currency >= currentSquare.getCost()) {
			return true;
		} else {
			return false;
		}

	}

	public ArrayList<Field> getAllAreasInField() {

		ArrayList<Field> fullFields = new ArrayList<Field>();
		for (int i = 0; i < this.ownedAreas.size(); i++) {
			Square squareOwned = this.ownedAreas.get(i);
			Field field = squareOwned.getField();
			int fieldNumOfAreas = squareOwned.getField().getAreas().size();
			for (int j = 0; j < this.ownedAreas.size(); j++) {
				if (this.ownedAreas.get(j).getField() == field) {
					fieldNumOfAreas--;
					if (fieldNumOfAreas == 0) {
						if (!fullFields.contains(field))
							fullFields.add(field);
					}
				}
			}

		}
		return fullFields;

	}

	public ArrayList<Field> getFieldsAllDevelopments(ArrayList<Field> fullFields) {

		ArrayList<Field> fieldsWithAllDevelopments = new ArrayList<Field>();

		for (int i = 0; i < fullFields.size(); i++) {

			boolean check = true;
			ArrayList<Area> areas = fullFields.get(i).getAreas();
			for (int j = 0; j < areas.size(); j++) {

				ArrayList<Development> developments = areas.get(j).getDevelopments();
				for (int k = 0; k < developments.size(); k++) {
					if (developments.get(k).getOwner() != this) {
						check = false;
					}
				}

			}

			if (check == true) {
				fieldsWithAllDevelopments.add(fullFields.get(i));
			}

		}
		return fieldsWithAllDevelopments;

	}

	public boolean ownsAllAMajorDevelopments(ArrayList<Field> fullFields) {

		for (int i = 0; i < fullFields.size(); i++) {

			int requiredCount = fullFields.get(i).getAreas().size();
			int myMajorDevs = 0;

			ArrayList<Area> areas = fullFields.get(i).getAreas();
			for (int j = 0; j < areas.size(); j++) {

				ArrayList<Development> developments = areas.get(j).getDevelopments();
				for (int k = 0; k < developments.size(); k++) {
					if (developments.get(k).getOwner() == this) {
						if (developments.get(k).isMajorDevelopment()) {
							myMajorDevs++;
						}
					}
				}
			}

			if (myMajorDevs == requiredCount) {
				return true;

			}
		}

		return false;
	}



}
