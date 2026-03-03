package save.our.planet;

import java.util.Scanner;

public class Area {

	public void offerOwnership(Player currentPlayer, Area area) {

		Scanner scanner = new Scanner(System.in);

		// Checking player's money
		if (currentPlayer.getCurrency() >= area.getCost()) {
			System.out.println(currentPlayer.getName() + ", would you like to buy " + area.getName() + " for "
					+ area.getCost() + "Volunteer Hours? Please enter y for yes, or n for no.");

			String choice = scanner.next();
			if (choice.equalsIgnoreCase("y")) {

				// Deducting cost
				int newCurrency = currentPlayer.getCurrency() - area.getCost();
				currentPlayer.setCurrency(newCurrency);

				// Setting ownership
				area.setOwner(currentPlayer);
				area.setIsOwned(true);

				System.out.println(currentPlayer.getName() + ", you now own" + area.getName());
				System.out.println("You now have " + newCurrency + " Volunteer Hours remaining.");

			} else if (choice.equalsIgnoreCase("n")) {
				System.out.println("You have chosen not to buy " + area.getName());
			} else {
				System.out.println("Invalid imput. Please enter y or n.");
			}
		} else {
			System.out.println("Sorry! You don't have enough volunteer hours to buy this area. Try again later!");
		}

	}

	public void payRent(Player currentPlayer, Area area) {

		Player owner = area.getOwner();

		int rent = area.getCost()/ 10;

		System.out.println(currentPlayer.getName() + " landed on " + area.getName() + " and will volunteer " + rent
				+ " Volunteer Hours to " + owner.getName());

		if (currentPlayer.getCurrency() < rent) {
			System.out.println(currentPlayer.getName() + " does not have enough Volunteer Hours to donate to "
					+ owner.getName() + "." + currentPlayer.getName() + ", you are out of the game.");
		} else {
			// Exchange of currency
			currentPlayer.setCurrency(currentPlayer.getCurrency() - rent);
			owner.setCurrency(owner.getCurrency() + rent);
			
			System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getCurrency() + " Volunteer Hours.");

			System.out.println(owner.getName() + " now has " + owner.getCurrency() + " Volunteer Hours.");

		}

	}
}
