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
	private static final String[] EMOJI_GAME_PIECES = { "🌳", "🐼", "🍀", "🌍" };

	static final Charity[] CHARITIES = {
		    new Charity(CharitySymbol.WAVE, 0),
		    new Charity(CharitySymbol.ROSE, 0),
		    new Charity(CharitySymbol.MOON, 0),
		    new Charity(CharitySymbol.EARTH, 0)
		};

	private static final int PASS_ACTIVATION_REWARD = 200;
	private static final int LAND_ACTIVATION_REWARD = 500;
	private static final int BOARD_SIZE = 12;
	private static final int ACTIVATION_INDEX = 0;
	private static final int GREEN_INDEX = 1;
	private static final String AREA = "AREA";
	private static final String GREEN = "GREEN";
	private static final String ACTIVATION = "ACTIVATION";

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
	
	public static void colourSettings (Scanner sc) {
		
		boolean check = true;
		
		while(check) {
		check=false;
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
			check=true;
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
		int rent =0;
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

		System.out.println("Auction starting for " + square.getName());

		int highestBid = 0;
		Player highestBidder;

		for (Player player : players) {
			if (player.getCurrency() <= 0)
				continue;

			System.out.println(player.getName() + "enter your bid (or 0 to skip): ");
			int bid = scanner.nextInt();

			if (bid > highestBid && player.getCurrency() >= bid) {
				highestBid = bid;
				highestBidder = player;
			}
		}

		if (highestBidder != null) {
			int newCurrency = highestBidder.getCurrency() - highestBid;
			highestBidder.setCurrency(newCurrency);
			square.setIsOwned(true);
			square.setOwner(highestBidder);

			System.out.println(highestBidder.getName() + "wins the auction for " + highestBid);
		} else {
			System.out.println("No one bid. Area remains unowned");
		}
	}
	
	
	/**
	 * ask to buy a major development
	 * @param p
	 * @param board
	 * @param scanner
	 */
	public static void askToBuyMajorDevelopment(Player p, Board board, Scanner scanner) {
		ArrayList<Area> ownedAreas = p.getOwnedAreas();

		String choice = "";

		while (true) {
			System.out.println(p.getName() + ", you own all the areas in a field!"
					+ " Would you like to carry out a development? If yes, enter y. If no, enter n: ");

			choice = scanner.next();

			if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
				break; // exit loop once valid
			} else {
				System.out.println("Invalid input. Please enter y or n.");
			}
		}

		boolean areaFound = false;

		if (choice.equalsIgnoreCase("y")) {

			System.out.println("Enter the name of the area you would like to develop: ");
			String areaChoice = scanner.next();

			for (Area area : ownedAreas) {
				String areaName = area.getName();

				if (areaName.equalsIgnoreCase(areaChoice)) {
					areaFound = true;
					ArrayList<Development> developmentAlreadyOwned = p.getDevelopments();
					
					boolean areaAlreadyDeveloped = false;
					
						if ((area.getMinorDevOwnedCounter() == 3 )&& (area.getMajorDevOwnedCounter()==1)) {
							areaAlreadyDeveloped = true;
							System.out.println("This area already has a major development. Please choose another");
							break;
						}
			

					if (areaAlreadyDeveloped == true) {
						break; // Exit the loop since area already developed
					}

					Development development = area.getDevelopments().stream().filter(Development::isMajorDevelopment)
							.findFirst().orElse(null);

					if (development == null) {
						System.out.println("No major development available.");
						break;
					}

					if (p.getCurrency() >= development.getCost()) {
						int newCurrency = p.getCurrency() - development.getCost();
						p.setCurrency(newCurrency);

						int developments = area.getMajorDevOwnedCounter();
						developments++;
						area.setMajorDevOwnedCounter(developments);

						System.out.println("Major development successful!");
					} else {
						System.out.println(
								"Sorry! You don't have enough volunteer hours to carry out a major development right now. Try again later!");
					}
					break; // Exit loop after processing the area choice
				}
			}

			if (!areaFound) {
				System.out.println("Area not found among your owned areas. Please try again.");
			}

		} else if (choice.equalsIgnoreCase("n")) {
			System.out.println("Maybe next time.");
		} else {
			System.out.println("Invalid input. Please enter y or n.");
		}
	}

	/**
	 * ask to buy development 
	 * @param p
	 * @param board
	 * @param scanner
	 */
	public static void askToBuyDevelopment(Player p, Board board, Scanner scanner) {

		ArrayList<Area> ownedAreas = p.getOwnedAreas();

		String choice = "";

		while (true) {
			System.out.println(p.getName() + ", you own all the areas in a field!"
					+ " Would you like to carry out a development? If yes, enter y. If no, enter n: ");

			choice = scanner.next();

			if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
				break; // exit loop once valid
			} else {
				System.out.println("Invalid input. Please enter y or n.");
			}
		}

		if (choice.equalsIgnoreCase("y")) {

			System.out.println("Enter the name of the area you would like to develop: ");
			String areaChoice = scanner.next();
			boolean areaFound = false;

			for (Area area : ownedAreas) {
				String areaName = area.getName();

				if (areaName.equalsIgnoreCase(areaChoice)) {
					areaFound = true;

					int ownedCount = area.getMinorDevOwnedCounter(); // How many developments already owned
					ArrayList<Development> developments = area.getDevelopments();

					if (ownedCount >= developments.size()) {
						System.out.println("You already own all developments on this area.");
						break;
					}

					Development nextDevelopment = developments.get(ownedCount);
					System.out.println("Next available development: " + nextDevelopment.getTitle());
					System.out.println("Description: " + nextDevelopment.getDescription());
					System.out.println("Cost: " + nextDevelopment.getCost());

					if (p.getCurrency() >= nextDevelopment.getCost()) {
						int newCurrency = p.getCurrency() - nextDevelopment.getCost();
						p.setCurrency(newCurrency);

						int updatedCount = area.getMinorDevOwnedCounter();
						ownedCount++;
						area.setMinorDevOwnedCounter(ownedCount);

						System.out.println("Development successful!");

					} else {
						System.out.println(
								"Sorry! You don't have enough volunteer hours to carry out a development right now. Try again later!");
					}
					break;
				}
			}

			if (!areaFound) {
				System.out.println("Area not found among your owned areas. Please try again: ");
			}

		} else if (choice.equalsIgnoreCase("n")) {
			System.out.println("Maybe next time.");
		} else {
			System.out.println("Invalid input.");

		}

	}
	
	/**
	 * show winner to screen
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
	 * add player to the arraylist 
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
	 * @param players
	 */
	public static void displayStats(ArrayList<Player> players) {

	    System.out.println("\n=========== PLAYER STATS ===========");

	    for (Player p : players) {
	    		System.out.println("Name: " + p.getName() +" Charity: " + p.getCharity().getIcon() +" | Volunteering Hours: " 
	    		+ p.getCurrency() +" | Position: " + p.getPosition()
	        );
	    }
	}
	
	/**
	 * check start status 
	 * @param oldPos
	 * @param newPos
	 * @param p
	 */
	public static void checkStartStatus(int oldPos, int newPos, Player p) {

		if (newPos == ACTIVATION_INDEX) {
			int newHours = p.getCurrency()+LAND_ACTIVATION_REWARD;
			p.setCurrency(newHours);
			System.out.println(p.getName() + " landed on Activation Zone and gained " 
					+ LAND_ACTIVATION_REWARD + " volunteering hours.");
		} else if (newPos < oldPos) {
			int newHours = p.getCurrency()+PASS_ACTIVATION_REWARD;
			p.setCurrency(newHours);
			System.out.println(p.getName() + " passed Activation Zone and gained " 
					+ PASS_ACTIVATION_REWARD + " volunteering hours.");
		}
	}

	/*
	 * creates board
	 */
	public static ArrayList<Square> createBoard() {

		ArrayList<Square> board = new ArrayList<>();

		for (int i = 0; i < BOARD_SIZE; i++) {

			String type;

			if (i == ACTIVATION_INDEX) {
				type = ACTIVATION;
			} else if (i == GREEN_INDEX) {
				type = GREEN;
			} else {
				type = AREA;
			}

			Square square = new Square(type, i);
			board.add(square);
		}

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
