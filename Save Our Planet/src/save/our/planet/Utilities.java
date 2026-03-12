/**
 * 
 */
package save.our.planet;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 */
public class Utilities {
	/*
	 * random
	 */
	private static final Random random = new Random();
	/*
	 * scanner
	 */
	private static final Scanner scanner = new Scanner(System.in);
	/*
	 * charity emojis
	 */
	private static final String[] EMOJI_GAME_PIECES = { "🌊", "🌷", "🌙", "🌍" };

	static final Charity[] CHARITIES = { new Charity(CharitySymbol.WAVE, 0), new Charity(CharitySymbol.ROSE, 0),
			new Charity(CharitySymbol.MOON, 0), new Charity(CharitySymbol.EARTH, 0) };

	private static final int PASS_ACTIVATION_REWARD = 200;
	private static final int LAND_ACTIVATION_REWARD = 500;
	private static final int ACTIVATION_INDEX = 0;

	public static final String RESET = "\033[0m";

	public static final String CYAN_BACKGROUND_BRIGHT = "\033[106m";
	public static final String BLACK_BACKGROUND = "\033[40m";
	public static final String RED_BACKGROUND_BRIGHT = "\033[101m";
	public static final String BLUE_BACKGROUND = "\033[44m";
	public static final String YELLOW_BACKGROUND_BRIGHT = "\033[103m";

	public static final String BLUE = "\033[34m";
	public static final String GREEN_BRIGHT = "\033[92m";
	public static final String WHITE_BRIGHT = "\033[97m";
	public static final String PURPLE_BRIGHT = "\033[95m";
	public static final String BLACK = "\033[30m";

	public static void colourSettings(Scanner sc) {

		boolean check = true;

		while (check) {
			check = false;
			System.out.println("pick colour settings");

			System.out.println(CYAN_BACKGROUND_BRIGHT + BLUE + " AQUA     type'a'" + RESET);
			System.out.println(BLACK_BACKGROUND + GREEN_BRIGHT + " RETRO    type'r'" + RESET);
			System.out.println(RED_BACKGROUND_BRIGHT + WHITE_BRIGHT + " BOLD     type'b'" + RESET);
			System.out.println(BLUE_BACKGROUND + PURPLE_BRIGHT + " POP      type'p'" + RESET);
			System.out.println(YELLOW_BACKGROUND_BRIGHT + BLACK + " CLEAN    type'c'" + RESET);

			String choice = sc.nextLine();
			switch (choice.toLowerCase()) {
			case "a": {
				System.out.println(CYAN_BACKGROUND_BRIGHT + BLUE + " AQUA selected");
				break;
			}
			case "r": {
				System.out.println(BLACK_BACKGROUND + GREEN_BRIGHT + " RETRO selected");
				break;
			}
			case "b": {
				System.out.println(RED_BACKGROUND_BRIGHT + WHITE_BRIGHT + " BOLD selected");
				break;
			}
			case "p": {
				System.out.println(BLUE_BACKGROUND + PURPLE_BRIGHT + " POP selected");
				break;
			}
			case "c": {
				System.out.println(YELLOW_BACKGROUND_BRIGHT + BLACK + " CLEAN selected");
				break;
			}
			case "n": {
				System.out.println(RESET);
				break;
			}
			default:
				System.out.println("invalid input");
				check = true;
			}

		}
	}

	/**
	 * offer ownership
	 * 
	 * @param currentPlayer
	 * @param scanner
	 * @param currentArea
	 */
	public void offerOwnership(Player currentPlayer, Scanner scanner, Area currentArea) {

		System.out.println(currentPlayer.getName() + ", would you like to buy " + currentArea.getName() + " for "
				+ currentArea.getCost() + " Volunteer Hours? (y/n)");

		while (true) {

			String choice = scanner.next().trim();

			if (choice.equalsIgnoreCase("y")) {

				if (currentPlayer.getCurrency() >= currentArea.getCost()) {

					// deduct cost and change ownership
					currentPlayer.setCurrency(currentPlayer.getCurrency() - currentArea.getCost());
					currentArea.setOwned(true);
					currentArea.setOwner(currentPlayer);

					System.out.println(currentPlayer.getName() + " now owns " + currentArea.getName());

				} else {

					System.out.println("Not enough funds to complete transaction.");

				}

				break;

			} else if (choice.equalsIgnoreCase("n")) {

				System.out.println("You chose not to buy " + currentArea.getName());
				break;

			} else {

				System.out.println("Invalid input. Please enter y or n.");

			}
		}
	}

	/**
	 * paying rent
	 * 
	 * @param currentPlayer
	 * @param currentArea
	 */
	public void payRent(Player currentPlayer, Area currentArea, ArrayList<Player> players) {
		Player owner = currentArea.getOwner();
		int developments = currentArea.getMinorDevOwnedCounter();
		int majorDev = currentArea.getMajorDevOwnedCounter();
		int rent = 0;
		currentArea.setOwner(owner);

		if (developments == 0) {
			rent = currentArea.getCost() / 10;
		} else if (developments == 1) {
			rent = currentArea.getCost() * 40 / 100;
		} else if (developments == 2) {
			rent = currentArea.getCost() * 70 / 100;
		} else if (developments == 3) {
			if (majorDev == 0) {
				rent = currentArea.getCost();
			} else if (majorDev == 1) {
				rent = currentArea.getCost() * 2;
			}

			System.out.println(currentPlayer.getName() + " landed on " + currentArea.getName() + " and must volunteer "
					+ rent + " Volunteer Hours to " + owner.getName());

			if (currentPlayer.getCurrency() >= rent) {

				currentPlayer.setCurrency(currentPlayer.getCurrency() - rent);
				owner.setCurrency(owner.getCurrency() + rent);

				System.out.println(
						currentPlayer.getName() + " now has " + currentPlayer.getCurrency() + " Volunteer Hours.");

				System.out.println(owner.getName() + " now has " + owner.getCurrency() + " Volunteer Hours.");
			} else {
				System.out.println(currentPlayer.getName() + " does not have enough Volunteer Hours to donate to "
						+ owner.getName() + ". " + currentPlayer.getName() + " is out of the game.");
				players.remove(currentPlayer);
			}

			ArrayList<Area> previousAreas = currentPlayer.getOwnedAreas();

			for (Area area : previousAreas) {
				area.setOwned(false);
				area.setDevelopments(null);
				area.setMajorDevOwnedCounter(0);
				area.setOwner(null);
			}
			players.remove(currentPlayer);
		}
	}

	/**
	 * 
	 * @param newPosition
	 * @param currentPlayer
	 */
	public static void showBoard(int newPosition, Player currentPlayer) {

		int index = newPosition - 1;

		System.out.println(boardArt[index]);
		System.out.println(currentPlayer + " is at" + squares[index]);

	}

	/**
	 * shows blank message
	 * 
	 * @param currentPlayer
	 */
	public static void showBlankMessage(Player currentPlayer) {

		System.out.println(currentPlayer + " has landed on the Green Zone."
				+ "Take time to rest and plan your next developments.");

	}

	/**
	 * shows landed on own square message
	 * 
	 * @param currentPlayer
	 */
	public static void showLandedOnOwnSquareMessage(Player currentPlayer) {
		System.out.println(currentPlayer + " has landed on an area they already own.");

	}

	/**
	 * auction off square if current player does not want to buy it
	 * 
	 * @param p
	 * @param square
	 * @param players
	 * @param scanner
	 */
	public static void auctionOff(Player p, Square square, ArrayList<Player> players, Scanner scanner) {

		System.out.println(
				"Dice roll starting for " + square.getName() + ".  Highest dice roll can purchase the area! 🎉");

		int highestRoll = 0;
		ArrayList<Player> highestRollers = new ArrayList<Player>();
		Area areaForSale = (Area) square;

		for (Player player : players) {
			if (player.getCurrency() < areaForSale.getCost()) {
				System.out.println(player.getName() + ", you don't have enough volunteer hours to buy this area ☹️");
				continue;
			}
			boolean validEntry = false;
			while (!validEntry) {

				System.out.println(player.getName()
						+ " If you want a chance to but his square, enter r to roll the dice. \nIf you don't want to but this area press n: ");
				String decision = scanner.next();

				int currentRoll = 0;
				if (decision.equalsIgnoreCase("r")) {
					currentRoll = diceRollForAuctionOff();

					if (currentRoll > highestRoll) {
						highestRoll = currentRoll;
						highestRollers.clear();
						highestRollers.add(player);
						System.out.println(player.getName() + ", you rolled a " + highestRoll
								+ ". You are currently the highest roller!");

					} else if (currentRoll < highestRoll) {
						System.out.println(player.getName() + ", you rolled a " + currentRoll
								+ ". You have lost the dice roll. ☹️ Better luck next time️");

					} else if (currentRoll == highestRoll) {
						highestRollers.add(player);
						System.out.println(player.getName() + ", you rolled a " + currentRoll
								+ ". You are currently tied for highestRoller!");
					}
					validEntry = true;

				} else if (decision.equalsIgnoreCase("n")) {
					System.out.println(player.getName() + "has opted out.");
					validEntry = true;
				} else {
					System.out.println("Invalid input, please try again");
				}

			}

		}

		while (highestRollers.size() > 1) {
			System.out.println("\n--- 🤝 TIE BREAKER! ---");
			System.out.print("The following players are tied: ");
			for (Player tp : highestRollers) {
				System.out.print(tp.getName() + " ");
			}
			System.out.println("\nOnly these players will roll again.");

			int tieHighRoll = 0;
			ArrayList<Player> tieWinners = new ArrayList<>();

			for (Player tiedPlayer : highestRollers) {
				boolean validTieRoll = false;
				while (!validTieRoll) {
					System.out.print(tiedPlayer.getName() + ", press 'r' to roll for the tie-breaker: ");
					String tieDecision = scanner.next();

					if (tieDecision.equalsIgnoreCase("r")) {
						int roll = diceRollForAuctionOff();
						System.out.println(tiedPlayer.getName() + " rolled a 🎲: " + roll);

						if (roll > tieHighRoll) {
							tieHighRoll = roll;
							tieWinners.clear();
							tieWinners.add(tiedPlayer);
						} else if (roll == tieHighRoll && tieHighRoll > 0) {
							tieWinners.add(tiedPlayer);
						}
						validTieRoll = true;
					} else {
						System.out.println("❌ Invalid! You must roll 'r' to continue the tie-breaker.");
					}
				}
			}

			highestRollers = tieWinners;
		}

		System.out.println("\n--- FINAL RESULTS ---");
		if (highestRollers.size() == 1) {
			Player winner = highestRollers.get(0);
			System.out.println("The winner is " + winner.getName() + "! 🏆");

			winner.setCurrency(winner.getCurrency() - areaForSale.getCost());
			winner.getOwnedAreas().add(areaForSale);
			areaForSale.setOwner(winner);

			System.out.println(winner.getName() + " paid " + areaForSale.getCost() + " volunteer hours.");
			System.out.println(winner.getName() + " now owns " + areaForSale.getName() + "!");
		} else {
			System.out.println("No one purchased the area. Auction closed.");
		}
	}

	public static int diceRollForAuctionOff() {

		int dice1 = random.nextInt(7) + 1;

		int dice2 = random.nextInt(7) + 1;

		int totalMove = 0;

		if (dice1 <= 6 && dice2 <= 6) {
			totalMove = dice1 + dice2;
		} else if (dice1 > 6 && dice2 > 6) {
			totalMove = 0;
		} else if (dice1 > 6 && dice2 <= 6) {
			totalMove = dice2;
		} else if (dice1 <= 6 && dice2 > 6) {
			totalMove = dice1;
		}

		return totalMove;
	}

	/**
	 * ask to buy a major development
	 * 
	 * @param p
	 * @param board
	 * @param scanner
	 */
	public static void askToBuyMajorDevelopment(Player p, ArrayList<Field> fieldsWithAllDevelopments, Scanner scanner) {

		ArrayList<Area> eligibleAreas = new ArrayList<Area>();
		ArrayList<Development> availableMajorDevs = new ArrayList<Development>();

		for (Field field : fieldsWithAllDevelopments) {
			ArrayList<Area> areasInField = field.getAreas();
			for (Area area : areasInField) {
				ArrayList<Development> developmentsInArea = area.getDevelopments();
				for (Development development : developmentsInArea) {
					if (development.isMajorDevelopment() == true) {
						int price = development.getCost();
						if (development.getOwner() == null) {
							if (p.getCurrency() >= price) {
								eligibleAreas.add(area);
								availableMajorDevs.add(development);

							}
						}

					}
				}
			}
		}

		if (eligibleAreas.isEmpty()) {
			System.out.println("\n" + p.getName()
					+ ", you don't have enough volunteer hours or eligible areas for a Major Development right now.");
			return;
		}
		System.out.print("\n" + p.getName()
				+ "Would you like to carry out a major development? If yes, enter y. If no, enter n: ");
		String answer = scanner.next();
		scanner.nextLine();
		if (answer.equalsIgnoreCase("y")) {
			System.out.println("\n--- 🏗️ MAJOR DEVELOPMENT MENU ---");
			System.out.println("Select an area to upgrade to a Major Development:");
			for (int i = 0; i < eligibleAreas.size(); i++) {
				Area currentArea = eligibleAreas.get(i);
				Development majorDev = availableMajorDevs.get(i);
				System.out.println("\n" + (i + 1) + ". " + currentArea.getName());
				System.out.println("  Major Development name: " + majorDev.getName());
				System.out.println("  Cost: " + majorDev.getCost());
				System.out.println("  Description: " + majorDev.getDescription());
			}
			System.out.println("Select the number that corresponds with the area you would like to develop: ");
			int choice = scanner.nextInt();

			if (choice > 0 && choice <= eligibleAreas.size()) {
				Area selectedArea = eligibleAreas.get(choice - 1);
				Development selectedDev = availableMajorDevs.get(choice - 1);
				p.setCurrency((p.getCurrency() - selectedDev.getCost()));
				selectedDev.setOwner(p);
				p.getDevelopments().add(selectedDev);
				selectedArea.setMajorDevOwnedCounter(selectedArea.getMajorDevOwnedCounter() + 1);

				System.out.println("🎉 Success! Development complete.");

			}
		}

	}

	/**
	 * ask to buy development
	 * 
	 * @param p
	 * @param board
	 * @param scanner
	 */
	public static void askToBuyDevelopment(Player p, ArrayList<Field> fullFields, Scanner scanner) {

		ArrayList<Area> eligibleAreas = new ArrayList<Area>();
		ArrayList<Development> availableDevs = new ArrayList<Development>();

		for (Field field : fullFields) {
			ArrayList<Area> areasInField = field.getAreas();
			for (Area area : areasInField) {
				ArrayList<Development> developmentsInArea = area.getDevelopments();
				for (Development development : developmentsInArea) {
					if (development.isMajorDevelopment() == false) {
						int price = development.getCost();
						if (development.getOwner() == null) {
							if (p.getCurrency() >= price) {
								eligibleAreas.add(area);
								availableDevs.add(development);

							}
							break;
						}

					}
				}
			}
		}

		if (eligibleAreas.isEmpty()) {
			System.out.println("\n" + p.getName()
					+ ", you don't have enough volunteer hours or eligible areas for a Development right now.");
			return;
		}
		System.out.print(
				"\n" + p.getName() + "Would you like to carry out a development? If yes, enter y. If no, enter n: ");
		String answer = scanner.next();
		scanner.nextLine();
		if (answer.equalsIgnoreCase("y")) {
			System.out.println("\n--- 🏗️ DEVELOPMENT MENU ---");
			System.out.println("Select an area to develop:");
			for (int i = 0; i < eligibleAreas.size(); i++) {
				Area currentArea = eligibleAreas.get(i);
				Development dev = availableDevs.get(i);
				System.out.println("\n" + (i + 1) + ". " + currentArea.getName());
				System.out.println("  Development name: " + dev.getName());
				System.out.println("  Cost: " + dev.getCost());
				System.out.println("  Description: " + dev.getDescription());
			}
			System.out.println("Select the number that corresponds with the area you would like to develop: ");
			int choice = scanner.nextInt();

			if (choice > 0 && choice <= eligibleAreas.size()) {
				Area selectedArea = eligibleAreas.get(choice - 1);
				Development selectedDev = availableDevs.get(choice - 1);
				p.setCurrency((p.getCurrency() - selectedDev.getCost()));
				selectedDev.setOwner(p);
				p.getDevelopments().add(selectedDev);
				selectedArea.setMinorDevOwnedCounter(selectedArea.getMinorDevOwnedCounter() + 1);

				System.out.println("🎉 Success! Development complete.");

			}
		}

	}

	/**
	 * show winner to screen
	 * 
	 * @param p
	 */
	public static void showWinnerScreen(Player p) {

		System.out.println("\n=================================");
		System.out.println("        GAME OVER");
		System.out.println("=================================");
		System.out.println("=================================");
		System.out.println("🏆🏆🏆 WINNER 🏆🏆🏆");
		System.out.println("🌍 The planet has been saved! 🌍");
		System.out.println("🎉 Congratulations " + p.getName() + "! 🎉");
		System.out.println("💚 Charity: " + p.getCharity());
		System.out.println("=================================");
		System.out.println("=================================\n");
		System.out.println("""
				--------------------------------------------------------
				        🌱 Your environmental impact was legendary!
				        Thank you for helping make the world better.
				--------------------------------------------------------
				""");

	}

	/**
	 * handle forfeit
	 * 
	 * @param p
	 * @param players
	 * @param board
	 */
	public static void handleForfeit(Player p, ArrayList<Player> players, Board board) {

		ArrayList<Area> previousAreas = p.getOwnedAreas();

		for (Area area : previousAreas) {
			area.setOwned(false);
			area.setDevelopments(null);
			area.setMajorDevOwnedCounter(0);
			area.setOwner(null);
		}

		if (players.size() >= 1) {

			System.out.println(p.getName() + " has forfeited the game. Their owned areas are now up for grabs!");
			players.remove(p);

		} else if (players.size() == 1) {

			Player winner = players.get(0);

			System.out.println(p.getName() + " has forfeited the game.");
			players.remove(p);

			showWinnerScreen(winner);
		}
	}

	/*
	 * displays game instructions
	 */
	public static void displayInstructions() {
		System.out.println("============================================");
		System.out.println("             GAME INSTRUCTIONS");
		System.out.println("============================================\n");

		// how to win
		System.out.println("HOW TO WIN:");
		System.out.println("\t- Be the first player to complete a Major");
		System.out.println("\t- Development on every Eco Area in one Ecosystem.");
		System.out.println("\t- If you run out of Volunteering Hours, you lose.\n");

		// turn order
		System.out.println("TURN ORDER:");
		System.out.println("\t- Players take turns in the order they entered the game.\n");

		// on a player's turn
		System.out.println("ON YOUR TURN:");
		System.out.println("\t- Press 'r' to roll the dice.");

		// dice outcomes
		System.out.println("\nDice Outcomes:");
		System.out.println("\t- Chance + Number: Move and receive 1 random chance.");
		System.out.println("\t- Two Chance Symbols: No movement, receive 2 chances.");
		System.out.println("\t- Two Numbers: Move the combined total.\n");

		System.out.println("BOARD SPACES:");
		// undeveloped areas
		System.out.println("\nUndeveloped Area:");
		System.out.println("\t- Choose 'b' to buy or 'n' to send to auction.");

		// developed area
		System.out.println("\nDeveloped Area:");
		System.out.println("\t- Donate Volunteering Hours if owned by another player.");
		System.out.println("\t- Nothing happens if you own it.");

		System.out.println("\nActivation Zone:");
		System.out.println("\t- Pass = +200 Hours");
		System.out.println("\t- Land = +500 Hours");

		System.out.println("\nGreen Zone:");
		System.out.println("\t- Take a break. No penalties.\n");

		System.out.println("DEVELOPMENTS:");
		System.out.println("\t- Own all areas in an Ecosystem to begin developing.");
		System.out.println("\t- Complete 3 Minor Developments before a Major Development.\n");

		System.out.println("ACTIONS:");
		System.out.println("\te - End Go");
		System.out.println("\tb - Buy");
		System.out.println("\td - Develop");
		System.out.println("\tn - Don't Buy");
		System.out.println("\ti - Instructions");
		System.out.println("\tf - Forfeit");
		System.out.println("\tp - Pause");
		System.out.println("\t\"name\" + status - View Stats");

		System.out.println("\n============================================");
	}

	/*
	 * displays dice roll
	 */
	public static int diceRoll() {

		int dice1 = random.nextInt(7); // 0–6
		int dice2 = random.nextInt(7); // 0–6

		int totalMove = 0;

		if (dice1 == 0) {
			System.out.println("Dice 1 rolled Chance!");
		} else {
			totalMove += dice1;
		}

		if (dice2 == 0) {
			System.out.println("Dice 2 rolled Chance!");
		} else {
			totalMove += dice2;
		}

		System.out.println("Dice rolled: " + dice1 + " and " + dice2);
		System.out.println("You move forward " + totalMove + " spaces.");

		return totalMove;
	}

	/*
	 * lets user choose number of players
	 */
	public static int chooseNumOfPlayers() {

		int numPlayers;

		while (true) {
			System.out.print("Enter number of players (2-4): ");

			if (scanner.hasNextInt()) {
				numPlayers = scanner.nextInt();
				scanner.nextLine(); // clear buffer

				if (numPlayers >= 2 && numPlayers <= 4) {
					break; // valid input
				} else {
					System.out.println("Invalid number. Please enter 2, 3 or 4.");
				}
			} else {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine(); // clear invalid input
			}
		}
		return numPlayers;
	}

	/*
	 * asks user to roll dice
	 */
	public static void askUserRoll() {

		String roll;

		while (true) {
			System.out.print("Press 'r' to roll the dice: ");
			roll = scanner.nextLine().toLowerCase();

			if (roll.equals("r")) {
				break;
			} else {
				System.out.println("Invalid input. Please press 'r' to roll.");
			}
		}
	}

	/**
	 * get player name
	 * 
	 * @return
	 */
	public static String getPlayerName() {
		String name;
		while (true) {
			System.out.println("Enter player name:");
			name = scanner.nextLine().trim();
			if (name.length() >= 1 && name.length() <= 10) {
				break;
			} else {
				System.out.println("Name must be between 1 and 10. Try again.");
			}
		}
		System.out.println("Player name recorded.");
		return name;
	}

	/**
	 * display available game pieces
	 * 
	 * @param taken
	 */
	public static void displayAvailableGamePieces(boolean[] taken) {
		System.out.println("\nChoose your token:");
		for (int i = 0; i < EMOJI_GAME_PIECES.length; i++) {
			String status = taken[i] ? " (taken)" : "";
			System.out.println((i + 1) + ") " + EMOJI_GAME_PIECES[i] + status);
		}
	}

	/**
	 * choose emoji token
	 * 
	 * @param taken
	 * @return
	 */
	public static String chooseEmojiToken(boolean[] taken) {

		while (true) {
			displayAvailableGamePieces(taken);
			System.out.print("Enter choice (1-" + EMOJI_GAME_PIECES.length + "): ");

			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();
				scanner.nextLine(); // clear buffer

				int index = choice - 1;

				if (index >= 0 && index < EMOJI_GAME_PIECES.length) {
					if (!taken[index]) {
						taken[index] = true;
						return EMOJI_GAME_PIECES[index];
					} else {
						System.out.println("Game piece already taken. Choose another.");
					}
				} else {
					System.out.println("Out of range. Try again.");
				}

			} else {
				System.out.println("Please enter a number.");
				scanner.nextLine(); // clear invalid input
			}
		}
	}

	/**
	 * get charity for emoji
	 * 
	 * @param emoji
	 * @return
	 */
	public static Charity getCharityForEmoji(String emoji) {

		for (int i = 0; i < EMOJI_GAME_PIECES.length; i++) {
			if (EMOJI_GAME_PIECES[i].equals(emoji)) {
				return CHARITIES[i];
			}
		}
		return null;
	}

	/**
	 * gets charity name which is linked to emoji
	 * 
	 * @param charity
	 * @return
	 */
	public static String getCharityName(Charity charity) {

		if (charity.getIcon() == CharitySymbol.WAVE) {
			return "The Nature Conservancy";
		} else if (charity.getIcon() == CharitySymbol.ROSE) {
			return "WWF";
		} else if (charity.getIcon() == CharitySymbol.MOON) {
			return "Green Peace";
		} else if (charity.getIcon() == CharitySymbol.EARTH) {
			return "Friends of the Earth";
		}

		return "Unknown Charity";
	}

	/**
	 * add player to the arraylist
	 * 
	 * @param n
	 * @return
	 */
	public static ArrayList<Player> addPlayers(int n) {

		ArrayList<Player> players = new ArrayList<>();
		boolean[] taken = new boolean[EMOJI_GAME_PIECES.length];

		for (int i = 1; i <= n; i++) {
			System.out.println("\n--- Player " + i + " Setup ---");

			String name = getPlayerName();
			String emoji = chooseEmojiToken(taken);
			Charity charity = getCharityForEmoji(emoji);

			Player player = new Player(name, charity, new ArrayList<>(), new ArrayList<>(), 1500);

			player.setPosition(1);

			players.add(player);

			System.out.println("Player " + i + ": " + name + " " + emoji + " | Charity: " + charity);
		}

		return players;
	}

	/**
	 * display statistics
	 * 
	 * @param players
	 */
	public static void displayStats(ArrayList<Player> players) {

		System.out.println("\n=========== PLAYER STATS ===========");

		for (Player p : players) {
			System.out.println("Name: " + p.getName() + " Charity: " + p.getCharity().getIcon()
					+ " | Volunteering Hours: " + p.getCurrency() + " | Position: " + p.getPosition());
		}
	}

	/**
	 * check start status
	 * 
	 * @param oldPos
	 * @param newPos
	 * @param p
	 */
	public static void checkStartStatus(int oldPos, int newPos, Player p) {

		if (newPos == ACTIVATION_INDEX) {
			int newHours = p.getCurrency() + LAND_ACTIVATION_REWARD;
			p.setCurrency(newHours);
			System.out.println(p.getName() + " landed on Activation Zone and gained " + LAND_ACTIVATION_REWARD
					+ " volunteering hours.");
		} else if (newPos < oldPos) {
			int newHours = p.getCurrency() + PASS_ACTIVATION_REWARD;
			p.setCurrency(newHours);
			System.out.println(p.getName() + " passed Activation Zone and gained " + PASS_ACTIVATION_REWARD
					+ " volunteering hours.");
		}
	}

	/**
	 * creates squares on board
	 * 
	 * @return
	 */

	public static ArrayList<Square> createBoard() {

		ArrayList<Square> board = new ArrayList<>();

		board.add(new Square(null, 0, false, false, "The Activation Zone"));
		board.add(new Square(null, 1, true, false, "The Green Zone"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The North Pole"), 2, false, false,
				"The North Pole"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The Atlantic Ocean"), 3, false, false,
				"The Atlantic Ocean"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The Amazon Rainforest"), 4, false, false,
				"The Amazon Rainforest"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The Pacific Ocean"), 5, false, false,
				"The Pacific Ocean"));
		board.add(
				new Square(new Field(null, new ArrayList<>(), 0, 0, "Delhi, India"), 6, false, false, "Delhi, India"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The Sumatran Rainforest"), 7, false, false,
				"The Sumatran Rainforest"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The South Pole"), 8, false, false,
				"The South Pole"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The Indian Ocean"), 9, false, false,
				"The Indian Ocean"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "The Congo Rainforest"), 10, false, false,
				"The Congo Rainforest"));
		board.add(new Square(new Field(null, new ArrayList<>(), 0, 0, "Seoul, South Korea"), 11, false, false,
				"Seoul, South Korea"));

		return board;
	}

	/**
	 * squares
	 */
	private static final String[] squares = { " Square 1: The Activation Zone", " Square 2: The Green Zone",
			" Square 3: The North Pole", " Square 4: The Atlantic Ocean", " Square 5: The Amazon Rainforest",
			" Square 6: The Pacific Ocean", " Square 7: Dehli, India", " Square 8: The Sumatran Rainforest",
			" Square 9: The South Pole", " Square 10: The Indian Ocean", " Square 11: The Congo Rainforest",
			" Square 12: Seoul, South Korea" };
	private static final String[] boardArt = {
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇▇▇▇▇▇▇▇▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇▇▇▇▇  ▇▇▇▇▇▇▇▇▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇▇▇▇▇  ▇▇▇▇▇▇▇▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇▇▇▇▇  ▇▇▇▇▇▇▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇▇▇▇▇  ▇▇▇▇▇▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇▇▇▇▇  ▇▇▇▇▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇▇▇▇▇▇▇▇▇▇▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇,   I         ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇   ▇▇▇▇             ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇▇!_▇▇▇▆                 ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇▇▇▌▇▇▇▅ ▇      ▇▇▇        +▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇,▇ ▆▇▇▇▇▇▇i    ▇▇▇▇     =▇    ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇▇▇▇▇▇▇▇▇       ▇▇▇      ▇▇▇▇  -▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇▇▇▇▇▇▇▇▇▇        |         l▇▇▇ ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇▇▇▇▇▇▇▇▇▇             I▇< ▇▇=▁▇▇#▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇           ▇▇▇▇▇▇▇▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇█▇▇▇▇▇▇▇▇▇▇▆         ▇▇▇▇▇▇▇▇▇▇▇▇ ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇▇▇▇▇▇▇▇▇▇▇▇▇# : I▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇  ▇▇▇▇▇▇▆▇▇▇▇▇▇▇▇▇▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇▇▇▇▇▇▇▇▇   ▇   I▓        ▇▇▇iI▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇ ▇ ░ -                      ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▆                      *▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇                  ▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇<          i▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇▇▇▇▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇▇▇▇▇▇▇▇▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇▇+   ▇▇▇▇▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇▇% ▇▇  ▇▇▇▇▇▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇▇▇▇▇▇   ▇▇▇▇▇▇▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇▇▇▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇            % ▇▇▇▇▇▇▇      ▇▇▇▇▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇             ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇               ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇                  ▂▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇                         ▇▇▇▇▇▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇                         ▇▇▇▇▇▇▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇ ▇▇▇▇▇▇▇▇                 ▇▇▇▇▇▇▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇ ▇▇▇▇▇▇▇                 ▒▇▇▇▇▇▇#▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇  ▇▇▇▇▇▇▇                 ▆▇▇▇▇▇  ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇   ▇▇▇▇▇                  ▇▇▇▇/   ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇   %▇▇▇                  ▇▇▇    ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇    ▇▇▇                         ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇    ▇▇                        ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇     ▇                      ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇.....▇.. ▇▇▇▇▇▇▌      =▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇.,,,▇▇▇▇▇▇▇▇▇▇▇   ▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇▄,,,,:▁▇▇▇.:▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇▇% ▃▇▇I       ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇▇▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇▇▇▇▇▇▇▇             ▇▇         ▇▇▇▇▇▇▇▇▇▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇▇▇▇▇▇▆%▇                ▇▇    ▇▇▇▆▇▇▇▇▇▇▇▇▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇▇▇▇▇                      ~▇▇▇▇▇▇▇▇     ▇▇▇▇▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇   ░▇▇ ▇                      ▇▇▇▇▇▇▇▇▇▇  ▇▇▇▇▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇         ▇  ▇▇▇▇              ▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇            ▇▇▇▇▇▇▇             ▇▇▇▇▇  ▇▇  ▇▇▇▇▇▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇           _▇▇▇▇▇▇▇▇▇▇:         %▇▇▇▇▇:   ▇▇▇▇▇▇▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇             <▇▇▇▇▇▇▇▇▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇               ▇▇▇▇▇▇▇▇           ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇              ▇▇▇▇▇▇            ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇              ▇▇▇▇▁             ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇             ▇▇               ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇            ▇l              ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇ ...... :,            =▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇ ...*▇▇▇,l▇▇▂     ▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇< ,▇▇▇▇▇▇▇▅:▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇        ▆▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇              ▇▇▇  ▇▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇                  ▁▇ ▆  ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇                       ▇  ~▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇                          ▇   ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇                           .▇ -▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇                            ▇▇▇ ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇                            ▇▇▇▇#▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇                             ▇▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇ ▌▇▇▇                       ,▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇▇▇▇▇▇                     ▇▇▇▇▇▇▇▇▇▇▇▇▇▇  ▇▇▇▇▇▇▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇ ▇▇▇▇       ......       ▇▇▇▇   ▇▇▇▇▇▇▇ ▇ ▇▇▇▇▇▇▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇▇▇         .......            ▇▇▇▇▇▇. ▇▇ ▇▇▇▇▇▇▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇▇;        ...,.|.▄▇         ▇▇▇▇▇▇▇      ▃▇▇▇▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇           ▇▇▇▇▇          ▇▇ ▇▇▇▆▇▇▇▇▇ ▇▇▇▇▇▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇     ▇▇▇▇▇▇▇▇▇▇▇      =▇▇     ▇▇▇▇▇▇▇▇▇▇▇▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇    ▆▇▇▇▇▇▇▇▇     ▇▇▇   %**>>; ▇▇▇▇▇▇▇▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇<          ,▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇              ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇  █ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ i ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇  ▅▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇   -▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇▇   ,▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇▇▇   ▇▇▇▆▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇!    +▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇▇▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇*      ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇▇▇▇▇ ▆▇▇  ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇      #▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ |▇▇▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇        ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇!▇▇▇▇▇▇       ▃▇▇▇  ▇▇▇▇          ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇▇▇▇▇▇,        ▇▃    ,▇▇.        ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇▇▇▇▇                 :          ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇▇▇▇                         ▇l▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇▇▇                       ▇▇▇▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇▇                     ▇▇▇▇▇▇▇▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇                   |▆.▒▇▇▇▇▇▇▇▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇              ....▇▇▇▇▇▇==<<<▀▇▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇<.........,<▇▇▇▇▇▇▇▇▇  ▇▇▇▇▇▇▇▇▀▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇     ▇▇▇▇▇▇▇▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇▇▇▇▇▇▇▇  ▇▇▇▇▇▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇▇▇     ▇▇▇▇▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇▇▇▇▇▇▇▇▇▇▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇▇▇▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇ ▇      ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇        -▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇▃▇ %  ▇▇▇▇▇▇▇▇▇▇▇▇▇           ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇▇▇▇   ▇▇▇~  ▇▇▇▇              +▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇        ▇     ▇!▇l              ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇▇              ▐                #▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇                                ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇▇                         %-      ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇!                      ▇▇▇▇▆%   ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇▇                   ▇▇▇▇▇▇▇▇▇   ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇                   ▇▇▇▇▇▇▇▇▇  ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇                  .....▃▇▇..▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇          ................▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇   ............,,,,,,,#▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇........,,,,,,::::▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇>,▂▇▇▇▇▇▇::|▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇▇▇▇▇▇▇▇▇▇▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇▇▇▇▇  ▇▇▇▇▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇▇▇▇  ▇▇▇▇▇▇▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇▇▇      ▇▇▇▇▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇▇▇ ▇▇▇  ▇▇▇▇▇▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇▇▇▇▇▇▇▇▇▇▇▀▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇▇▇▇▇▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇              ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇                    ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇                        ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇                          ▇▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇  ▇▇▇                       ▇▇▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇ ▇▇▇*                   l▇▇▇▇▇▇▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇ ▇▇▇          ▇▇▇▇▁▇▇   ,  ▇▇▇▇▇▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇▌▇▇          .▇▇▇▇▇          !▇▇>▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇  ▇▇;      ▇▇▇▇▇▇▇▇▇l           ▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇  ▒        ▇▇▇▇▇▇▇▇▇▇,            ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇           ▇▇▇▇▇▇▇▇▆            ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇             l ▇▇▇              ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇                              ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇                            ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇                      -▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇           ▇▇▇▇;  ▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇▌    ▆▇▇▇▇▇ ▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇▇      ▇▇▇▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆▇▇▇▇▇▇  ▇▇▇▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇▃▇▇▇▇▇▇  ▇▇▇▇▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇▇▇▇▇▇▇▇  ▇▇▇▇▇▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇▅▇▇▇▇▇▇  ▇▇▇▇▇▇▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇▄▇▇▇▇▇▇▇▇▇▇▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇▅▇▇▇▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇%▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇▇▇_    ▇▇▇▇▇▇▇▇▇▇▇▇ ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇  ▇▇▇     ▇▇▇   ▇▇▇▇▇▇▆ ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇▇l .        ▇     ▇▇▇     ~▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇▇▇▇▇▇               ▂         ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇▇▇▇▇                          ~▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇▇▇▇▇                            ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇▇▇▇▇▇                           >▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ ▇▇▇▇                       ▂▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇ %▇▇▇,                    ▇▇▇▇▇▇  ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇ ▅▇▇▆                    ▇▇▇▇▇▇ ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇   ▇                        ▇▇▇ ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇                           ▇▇ ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇               ...........  ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇▇▇▇          .▇▇▇▇▇▇:........▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▐▇▇▇▇▇▇▇▇     .>▇▇▇▇▇▇▇▇I,,,,,.*▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇▇▇▇  :▇▇▇▇▇▇▇▇  ../▇▇▇▇▇▇▇▇▅,,,,▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇▇▇▇▇  ▇▇  ▇▇▇▇▇▇▇▇▇▇<....,▇▇▃,,l▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇▇▇▇▇  ▇▇  ▇▇▇▇▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇▅▇▇      ▇▇▇▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇▇▇▇▇▇▇▇▇▇▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇▇▇▇▇▇▇▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅▇▀▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇   ▇▇▆ ▇▀▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇              ▇▇▇▇▄▇▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇     ▇▇▇▇▇▇▅▇▇i  ▇▇▇▇▇▇▇▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇  ▇▇▇ ▇▇▇▇▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇     ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇  ▇▇▇▇ ▆▇▇▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇     ▃▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▌      ▀▇▇▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇             ▇▇▇▇▇▇▇▇▇▇_      ▇▇▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇             ▇▇▇▇▇▇▇▇▇        ▇ ▇▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇               ▇▇▇▇▇▇▇            ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇              ▇▇▇▇▇▇▇▇           ;▇              ▇▇   l  \n"
					+ "      ▇▇▇▇▇▇     \\▇▇▇▇▇▇              ▇▇▇▇▇.            ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇▇▇▇▇ <▇▇  ▇▇▇▇▇▇              ▇▇▇▇              ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇▇▇▇▇      ▇▇▇▇▇▇▇                              ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇▇▇▇▇▇  +▇▇▇▇▇▇░▇▇                            ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇▇▇▇▇  ▇▇▇▇▇▇▇▇▇ ▇▇  . ....                  ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇▇▇▇▇▇▇▇▇▇▇▓     ▂▇▇......................<▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇▇▇▇▇▇▇   ▇▇▌     ▇▇▇,,,,,,,,..........▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇*:▇▇▇▇▇▇▇▇▇l▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇▇▇▇▇    ▇▇▇▇       ▇▇▇              ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇▇▇▇▇▇▇▇▇*         ▇▇| ▇.             >▇▇▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇▅▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇~    ▒▇ ▇▇▇>*▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇▇  ▇▇    ▇▇▇▄▇▇▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇  >\\   *▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇▇▇  ▇  ▇▇  ▇▇▇▇▇▇ ▇▇_▇▇▇▇▇▇▇▇▇▇▇▇▇▇~  |        ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇▇▇  ▇  ▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇            %▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇▇▇  ▇▌ ▇▇  ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇             ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇▇▇▇  ▇▇▇   ▇▇▇▇▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇              ▐▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇,▇▓ ▇▇▇▇▇▇▇▇▇▇▇▇▇ ▇               ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇                 ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇   ▇▇▇▅ ▇▇▇▇▇▇!                 ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇   :▇   ▇▇▇%                    ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇        ,▇▇                   ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇       !                    ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▇             ▌▇▇▇▇▇▇▇=█▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇        _▇▇▇▇▇▇▇▇▇▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇>     ░▇▆ .!▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇       ▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇            ▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇              ▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇▇▇▇▇▇▇             ▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇▇▇▇▇▇▇▇▇▇            ▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇▇▇▇     ▇▇▇▇           ▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇▇▇▇▇ \\▇▇▇▇▇▇▇▇          ▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇▇▇▇▇▇▇▇    ▇▇▇▇▇▇         ▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇▇▇▇▇▇▇▇i▇▇  ▇▇▇▇▇▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇              ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇▅▇▇▇▇▇▇▇▇▇▇▇                    ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇▇▇▇▇▇▇                        ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇                          +▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇                              ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇                              -▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇                                ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇                                #▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇                                  ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇                                  ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇                                ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇                                ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇                              ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇                            ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▆                      *▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇                  ▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇=          ;▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "",
			"                                       ▇                                        \n"
					+ "                                      ~▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                    ▇▇          ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇▇                    \n"
					+ "                    ▇▇▇   ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇       ▇▇▇▇▇l   ▇▇                     \n"
					+ "                      ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇            ▇▇▇▇▇                      \n"
					+ "                    ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇        ▇▇▇▇▇                    \n"
					+ "                  ▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇       ▇▇   ▇▇▇                  \n"
					+ "                ▇▇       ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇      ▇▇      ,▇▇                \n"
					+ "              ▇▇   ▆▇▇▇▇  ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇     ▇▇ >▇▇▇    ▇▇▇              \n"
					+ "             ▇▇    ▇█      ▇▇▇▇▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇ _▇  ▇▇     ▇▇             \n"
					+ "      ▇▇▇> ▇▇       ▇▇▇▇    ▇▇▇▇▇▇▇▇▇▇▇▇▇         ▇▇     ▇▇▇      ▇▇▇  ▇▇▇      \n"
					+ "         ▇▇▇       ▀  ▇▇     ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇   ▇▇    ▇▇▇          ▇▇▇█        \n"
					+ "         ▇▇ ▇▇▇    ▇▇▇▇       ▇▇▇              ▇▇▇    ▇▇▇▇▇▇    ▆▇▇▇ ▇▇         \n"
					+ "        ▇▇     ▇▇▇*         ▇▇                    ▇▇         ▇▇▇▇     ▇i        \n"
					+ "        ▇!        ▇▇▇▇    ▇▇                        ▇▇    ▇▇▇I        ▇▇        \n"
					+ "       ▇▇ ▇▇  ▇▇▇▇   ░▇▇▇▇▇                          +▇▇▇▇    ▇▇▇▇▇    ▇▇       \n"
					+ "       ▇  ▇▇ ▇▇  ▇▇     ▇                              ▇         ▇▇    ▁▇       \n"
					+ "      ▇▇  ▇▇ ▇▇   ▇    ▇▇                              -▇      ▇▇▇▇     ▇▇      \n"
					+ "      ▇▇  ▇▇ i▇  ▇▇   ▇▇                                ▇▇   ▆▇  ▇▇     ▇▇      \n"
					+ "      ▇+  ▇▇   ▇▇▇    ▇▇                                #▇    ▁▇▇▇      ▇▇      \n"
					+ " ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇                                  ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ \n"
					+ "  <   ▇▇              ▇                                  ▇              ▇▇   l  \n"
					+ "      ▇▇    ▇▇▇▇▇█    ▇▇                                ▇▇      ▇▇      ▇▇      \n"
					+ "      ▇▇    ▇=  ▇▇    I▇                                ▇▇     ▇ ▇      ▇▇      \n"
					+ "      I▇    ▇▇▇▇▇▇     ▇▇                              ▇▇    ▅▇  ▇      ▇▁      \n"
					+ "       ▇▇     ▇▇<      @▇▇                            ▇▇     ▇▇▇▇▇▇.   ▇▇       \n"
					+ "       ▆▇    ▇▇     ▇▇▇▇ ▇▇                          ▇▇ ▇▇▇I     ▇     ▇▇       \n"
					+ "        ▇▇       ▇▇▇▓     ▂▇▆                      *▇▇     ▇▇▇▇       ▇▇        \n"
					+ "         ▇▇   ▇▇▇   ▇▇▌     ▇▇▇                  ▇▇▇   %**>>; ▇▇▇▇   ▇▇         \n"
					+ "          ▇▇▇▇    ▇▇  ▇▇      ▇▇▇▇=          ;▇▇▇▇     ▇▇        l▇▇▇▇          \n"
					+ "       ▇▇▇▇▇▇      ▇▇▇▇      ▇▇    ▇▇▇▇▇▇▇▇▇▇    ▇▇    ▇▇▇▇▇       ▇▇▇▇▇▇       \n"
					+ "      ▇▇▇   ▇▇    ▇▇  ▇▇    ▇▇         ▇▇         ▇▇       ▇▇     ▇▇   ▇▇▆      \n"
					+ "             ▇▇:  ▇▇▇▇▇▇   ▇▇ ▇▇▇▇▇▇   ▇▇    ▇▇    ▇▇  ▇▇▇▇▇    ▐▇▇             \n"
					+ "              \\▇▇         ▇▆      ▇▇   ▇▇   ▇▇      ▇▇         ▇▇               \n"
					+ "                ▇▇▇      ▇-      ▇▇    ▇▇  ▇▇▇▇▇▇    ▇▇      ▇▇                 \n"
					+ "                  ▇▇▇▅ l▇       ▇▇     ▇▇  ▇   ▇▇     ▇▇  ▂▇▇                   \n"
					+ "                     ▇▇▇*      ▇▇      ▇▇   ▇▇▇▇       ▇▇▇▇                     \n"
					+ "                     ▇▇ ▇▇▇▇|          ▇▇          \\▇▇▇▇ ▇▇                     \n"
					+ "                    ▇▇▇     ▇▇▇▇▇▇▇=   ▇▇   ,▇▇▇▇▇▇▇     ▇▇▇                    \n"
					+ "                                   ▂▇▇▇▇▇▇▇▇▓                                   \n"
					+ "                                       ▇▇                                       \n"
					+ "                                       ▇▇                                       \n"
					+ "                                                                                \n" + "" };

}
