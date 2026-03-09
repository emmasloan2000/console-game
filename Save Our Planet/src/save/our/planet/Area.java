<<<<<<< HEAD
/**
 * -
 */
package save.our.planet;

import java.util.ArrayList;

/**
 * 
 */
public class Area extends Square {

	private int cost;
	private Player owner;
	private ArrayList<Development> developments;
	private int minorDevOwnedCounter;
	private int majorDevOwnedCounter;

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
=======
package save.our.planet;

import java.util.Scanner;

public class Area {

	private String areaName;
	private int cost;
	private boolean isOwned;
	private Player owner;
	private int devOwnedCounter;

	public Area(String areaName, int cost) {
		this.areaName = areaName;
		this.cost = cost;
		this.isOwned = false;
		this.owner = null;
		this.devOwnedCounter = 0;

	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isOwned() {
		return isOwned;
	}

	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player player) {
		this.owner = player;
		this.isOwned = (player != null);
	}

	public int getDevOwnedCounter() {
		return devOwnedCounter;
	}

	public void setDevOwnedCounter(int devOwnedCounter) {
		this.devOwnedCounter = devOwnedCounter;
	}

	public void offerOwnership(Player currentPlayer) {

		Scanner scanner = new Scanner(System.in);

		// Checking player's money
		if (currentPlayer.getCurrency() >= cost) {
			System.out.println(currentPlayer.getName() + ", would you like to buy " + areaName + " for " + cost
					+ "Volunteer Hours? Please enter y for yes, or n for no.");

			String choice = scanner.next();
			if (choice.equalsIgnoreCase("y")) {

				// Deducting cost & setting ownership
				currentPlayer.setCurrency(currentPlayer.getCurrency() - cost);
				this.owner = currentPlayer;
				this.isOwned = true;

				System.out.println(currentPlayer.getName() + ", you now own " + areaName);
				System.out.println("You now have " + currentPlayer.getCurrency() + " Volunteer Hours remaining.");

			} else if (choice.equalsIgnoreCase("n")) {
				System.out.println("You have chosen not to buy " + areaName);
			} else {
				System.out.println("Invalid input. Please enter y or n.");
			}
		} else {
			System.out.println("Sorry! You don't have enough volunteer hours to buy this area. Try again later!");
		}

	}

	public void payRent(Player currentPlayer) {

		Player owner = this.owner;

		int developments = this.devOwnedCounter;
		int rent;

		if (developments == 0) {
			rent = cost / 10;
		} else if (developments == 1) {
			rent = cost * 40 / 100;
		} else if (developments == 2) {
			rent = cost * 70 / 100;
		} else {
			rent = cost;
		}

		System.out.println(currentPlayer.getName() + " landed on " + areaName + " and must volunteer " + rent
				+ " Volunteer Hours to " + owner.getName());

		if (currentPlayer.getCurrency() >= rent) {

			currentPlayer.setCurrency(currentPlayer.getCurrency() - rent);
			owner.setCurrency(owner.getCurrency() + rent);

			System.out
					.println(currentPlayer.getName() + " now has " + currentPlayer.getCurrency() + " Volunteer Hours.");

			System.out.println(owner.getName() + " now has " + owner.getCurrency() + " Volunteer Hours.");
		} else {
			System.out.println(currentPlayer.getName() + " does not have enough Volunteer Hours to donate to "
					+ owner.getName() + ". " + currentPlayer.getName() + " is out of the game.");
		}

>>>>>>> branch 'master' of git@gitlab.eeecs.qub.ac.uk:csc7053-2526/csc7053-2526-G11.git
	}

}
