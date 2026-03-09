/**
 * 
 */
package save.our.planet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */
public class Utility {
	
	public static void auctionOff(Player p, Square square, ArrayList<Player> players, Scanner scanner) {
		

		
		System.out.println("Auction starting for " + square.getName());
		
		int highestBid = 0;
		Player highestBidder = null;
		
		for (Player player : players) {
			if (player.getCurrency <= 0) 
				continue;
			
			System.out.println(player.getName() + "enter your bid (or 0 to skip): ");
			int bid = scanner.nextInt();
			
			if (bid > highestBid && player.getCurrency() >= bid ){
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
	            String areaName = area.getAreaName();

	            if (areaName.equalsIgnoreCase(areaChoice)) {
	                areaFound = true;

	                ArrayList<Development> developmentAlreadyOwned = p.getDevelopments();

	                boolean areaAlreadyDeveloped = false;
	                for (Development d : developmentAlreadyOwned) {
	                    if (d.getIsMajorDevelopment() && d.getArea().equals(area)) {
	                        areaAlreadyDeveloped = true;
	                        System.out.println("This area already has a major development. Please choose another");
	                        break;
	                    }
	                }

	                if (areaAlreadyDeveloped) {
	                    break;  // Exit the loop since area already developed
	                }

	                Development development = area.getDevelopments().stream()
	                        .filter(Development::isMajorDevelopment)
	                        .findFirst()
	                        .orElse(null);

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
	                break;  // Exit loop after processing the area choice
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
			String areaName = area.getAreaName();
			
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
					developments++;
					area.setMinorDevOwnedCounter(developments);	
					
					System.out.println("Development successful!");
					
			}else {
				System.out.println("Sorry! You don't have enough volunteer hours to carry out a development right now. Try again later!");
			}
				break;
			}
		}
		
		if (!areaFound) {
			System.out.println("Area not found among your owned areas. Please try again: ");
		}
		
		}else if (choice.equalsIgnoreCase("n")) {
			System.out.println("Maybe next time.");
		}else {
			System.out.println("Invalid input.");
		
	}
		
			
		}
	
	
	
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
	
	
	public static void handleForfeit(Player p, ArrayList<Player> players, Board board) {

	    players.remove(p);

	    ArrayList<Square> squares = board.getSquares();
	    ArrayList<Area> ownedAreas = p.getOwnedAreas();

	    if (players.size() >= 1) {

	        System.out.println(p.getName() + " has forfeited the game. Their owned areas are now up for grabs!");

	        for (Square square : squares) {
	            for (Area area : ownedAreas) {

	                if (area.getAreaName().equals(square.getSquareName())) {

	                    square.setOwner(null);
	                    square.setMinorDevelopmentCounter(0);
	                    square.setMajorDevelopmentCounter(0);
	                    square.setIsOwned(false);
	                }
	            }
	        }

	    } else if (players.size() == 1) {

	        Player winner = players.get(0);

	        System.out.println(p.getName() + " has forfeited the game.");

	        showWinnerScreen(winner);
	    }
	}
		
		
		
		
		
		
		
		
	

}
