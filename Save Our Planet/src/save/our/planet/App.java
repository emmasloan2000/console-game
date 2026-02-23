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
        ArrayList<Square> squares = Utility.createBoard();
        Board gameBoard = new Board(squares, 0, 0, 0, 0);

        Utility.colourSettings(); // Requirement: Choice of color palette 
        int numOfPlayers = Utility.chooseNumOfPlayers(); // Requirement: 2-4 players
        sc.nextLine(); // Flush the scanner buffer 

        ArrayList<Player> players = Utility.addPlayers(numOfPlayers);
        for (int i = 0; i < players.size(); i++) {
            String playerName = Utility.getPlayerName(); // Requirement: Input player names 
            String charityName = Utility.getCharityName(); // Requirement: Represents a charity
            players.get(i).setName(playerName);
            players.get(i).setCharity(charityName);
            players.get(i).setCurrency(1500); // Requirement: Starting balance of 1500 hours 
        }

        Utility.displayStats(players); // Show initial stats and balances 
        Utility.displayInstructions(); // Requirement: Show aim and rules 

        int currentPlayerCount = 0;
        Player winner = null;
        boolean gameOn = true;

        // --- MAIN GAME LOOP ---
        while (gameOn) {

            Player currentPlayer = players.get(currentPlayerCount);
            System.out.println("\nIt is " + currentPlayer.getName() + "'s turn.");

            Utility.askUserRoll(); // Prompt: "press r to roll" 
            String response = sc.nextLine();

            if (response.equalsIgnoreCase("r")) {
                // 1. DICE ROLL & CHANCE LOGIC
                int dice1 = Utility.diceRoll();
                int dice2 = Utility.diceRoll();
                int totalRoll = 0;

                // Check Dice 1: Number vs. Chance Symbol
                if (dice1 <= 6) {
                    totalRoll += dice1;
                } else {
                    Utility.randomChance(currentPlayer); // Handle random effects 
                }

                // Check Dice 2: Number vs. Chance Symbol 
                if (dice2 <= 6) {
                    totalRoll += dice2;
                } else {
                    Utility.randomChance(currentPlayer); // Handle random effects 
                }

                // 2. MOVEMENT LOGIC 
                if (dice1 <= 6 || dice2 <= 6) {
                    int oldPosition = currentPlayer.getPosition();
                    int newPosition = gameBoard.findNewPosition(oldPosition, totalRoll);

                    // Requirement: Receive donations for passing/landing on start 
                    Utility.checkStartStatus(oldPosition, newPosition, currentPlayer);

                    gameBoard.addPlayerToSquare(newPosition,currentPlayer);
                    currentPlayer.setPosition(newPosition);
                    Square currentSquare = gameBoard.findPlayerSquare(newPosition);
                    Utility.showBoard(newPosition,currentPlayer);//prints correspodning board image and text of where current player is

                    // 3. LANDING ACTIONS 
                    if (currentSquare.getIsBlank() == true) { // Green Zone logic 
                        Utility.showBlankMessage();
                    } else if (currentSquare.getIsOwned() == true) {
                        if (currentSquare.checkIfOwner(currentPlayer) == true) {
                            Utility.showLandedOnOwnSquareMessage();
                        } else {
                            Utility.payRent(currentPlayer, currentSquare); // 10% volunteer hours 
                        }
                    } else if (currentSquare.getIsOwned() == false) {
                        if (currentPlayer.hasFunds(currentSquare) == true) {
                            // Opportunity to buy or auction 
                            Utility.offerOwnership(currentPlayer, currentSquare);
                            if (currentSquare.getIsOwned() == false) {
                                Utility.auctionOff(currentPlayer, currentSquare, players);
                            }
                        }
                    }
                } // End of Movement Block

                // 4. DEVELOPMENT LOGIC
                // Allowed from anywhere on the board if requirements are met 
                if (currentPlayer.ownsAllAreasInField(gameBoard) == true) {
                    if (currentPlayer.ownsAllDevelopments(gameBoard) == true) {
                        Utility.askToBuyMajorDevelopment(currentPlayer, gameBoard); // [
                    } else {
                        Utility.askToBuyDevelopment(currentPlayer, gameBoard); //
                    }
                }

                // 5. WIN CONDITION CHECK 
                if (currentPlayer.ownsAllMajorDevelopments(gameBoard) == true) {
                    gameOn = false;
                    winner = currentPlayer;
                    Utility.showWinnerScreen(winner);
                    break;
                }

                // 6. ITERATE TURNS 
                if (currentPlayerCount < (numOfPlayers - 1)) {
                    currentPlayerCount++;
                } else {
                    currentPlayerCount = 0;
                }

            } else if (response.equalsIgnoreCase("f"))
                Utility.handleForfeit(currentPlayer, players);
            } else if (response.equalsIgnoreCase("p"))
            	Utility.displayStats(players);
                System.out.println("game paused, enter any key to resume");
            	String holder = sc.next();
			}
		} // End of while loop
	} // End of main method
} // End of App class