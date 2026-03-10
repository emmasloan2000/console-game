package save.our.planet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main application class for the "Save the World" Virtual Board Game. Handles
 * game setup, turn iteration, and core game logic.
 */
public class App {

	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // --- GAME INITIALIZATION ---
        ArrayList<Square> squares = Utilities.createBoard();
        Board gameBoard = new Board(squares, 0, 0, 0, 0);

        Utilities.colourSettings(); // Requirement: Choice of colour palette
        int numOfPlayers = Utilities.chooseNumOfPlayers(); // Requirement: 2-4 players
        sc.nextLine(); // Flush the scanner buffer 

        ArrayList<Player> players = Utilities.addPlayers(numOfPlayers);
        for (int i = 0; i < players.size(); i++) {
            String playerName = Utilities.getPlayerName(); // Requirement: Input player names 
            Charity charity = Utilities.getCharitySelection(); // Requirement: Represents a charity
            players.get(i).setName(playerName);
            players.get(i).setCharity(charity);
            players.get(i).setCurrency(1500); // Requirement: Starting balance of 1500 hours 
        }

        Utilities.displayStats(players); // Show initial status and balances 
        Utilities.displayInstructions(); // Requirement: Show aim and rules 

        int currentPlayerCount = 0;
        Player winner = null;
        boolean gameOn = true;

        // --- MAIN GAME LOOP ---
        while (gameOn) {

            Player currentPlayer = players.get(currentPlayerCount);
            System.out.println("\nIt is " + currentPlayer.getName() + "'s turn.");
            Utilities.displayStats(currentPlayer);

            Utilities.askUserRoll(); // Prompt: "press r to roll" 
            String response = sc.nextLine();

            if (response.equalsIgnoreCase("r")) {
                // 1. DICE ROLL & CHANCE LOGIC
                int dice1 = Utilities.diceRoll();
                int dice2 = Utilities.diceRoll();
                int totalRoll = 0;

                // Check Dice 1: Number vs. Chance Symbol
                if (dice1 <= 6) {
                    totalRoll += dice1;
                } else {
                   //randomChance(currentPlayer); // Handle random effects 
                }

                // Check Dice 2: Number vs. Chance Symbol 
                if (dice2 <= 6) {
                    totalRoll += dice2;
                } else {
                   // randomChance(currentPlayer); // Handle random effects 
                }

                // 2. MOVEMENT LOGIC 
                if (dice1 <= 6 || dice2 <= 6) {
                    int oldPosition = currentPlayer.getCharity().getLocation();
                    int newPosition = gameBoard.findNewPosition(oldPosition, totalRoll);

                    // Requirement: Receive donations for passing/landing on start 
                    Utilities.checkStartStatus(oldPosition, newPosition, currentPlayer);

                    gameBoard.addPlayerToSquare(newPosition,currentPlayer);
                    currentPlayer.getCharity().setLocation(newPosition);
                    Square currentSquare = gameBoard.findPlayerSquare(newPosition);
                    Utilities.showBoard(newPosition,currentPlayer);//prints correspodning board image and text of where current player is

                    // 3. LANDING ACTIONS 
                    if (currentSquare.isBlank() == true) { // Green Zone logic 
                        Utilities.showBlankMessage(currentPlayer);
                    } else if (currentSquare.isOwned() == true) {
                        if (currentSquare.checkIfOwner(currentPlayer) == true) {
                            Utilities.showLandedOnOwnSquareMessage(currentPlayer);
                        } else {
                            Utilities.payRent(currentPlayer, currentSquare); // 10% volunteer hours 
                        }
                    } else if (currentSquare.isOwned() == false) {
                        if (currentPlayer.hasFunds((Area) currentSquare) == true) {
                            // Opportunity to buy or auction 
                            Utilities.offerOwnership(currentPlayer, currentSquare);
                            if (currentSquare.isOwned() == false) {
                                Utilities.auctionOff(currentPlayer, currentSquare, players, sc);
                            }
                        }
                    }
                } // End of Movement Block

                // 4. DEVELOPMENT LOGIC
                // Allowed from anywhere on the board if requirements are met 

                ArrayList<Field> fullFields = currentPlayer.getAllAreasInField();// returns list of fields the player owns all areas in
                ArrayList<Field> fieldsWithAllDevelopments = new ArrayList<Field>();// list of fields with all areas that have all developments
                if (!fullFields.isEmpty()) { 
                	fieldsWithAllDevelopments = currentPlayer.getFieldsAllDevelopments(fullFields);

                    if (!fieldsWithAllDevelopments.isEmpty()) {
                    	
                        Utilities.askToBuyMajorDevelopment(currentPlayer, fieldsWithAllDevelopments); // 
 
                    } else {

                        Utilities.askToBuyDevelopment(currentPlayer, fullFields); //

                }

                // 5. WIN CONDITION CHECK 
                if (currentPlayer.ownsAllAMajorDevelopments(fullFields) == true) {
                    gameOn = false;
                    winner = currentPlayer;
                    Utilities.showWinnerScreen(winner);
                    break;
                }

                // 6. ITERATE TURNS 
                if (currentPlayerCount < (numOfPlayers - 1)) {
                    currentPlayerCount++;
                } else {
                    currentPlayerCount = 0;
                }


            } else if (response.equalsIgnoreCase("f")) {
                Utilities.handleForfeit(currentPlayer, players, gameBoard);
            } else if (response.equalsIgnoreCase("p")) {
            	Utilities.displayStats(players);
                System.out.println("game paused, enter any key to resume");
            	String holder = sc.next();
			} else if (response.equalsIgnoreCase("i")) {
				Utilities.displayInstructions();
            	System.out.println("game paused, enter any key to resume");
            	String holder = sc.next();
			}else {
            	System.out.println("please enter a valid command");
			}
		} // End of while loop
} // End of main method
} // End of App class