/**
 * 
 */
package save.our.planet;

import java.util.Scanner;

/**
 * 
 */
public class Utility {
	
	public static void auctionOff(Player p, Square square, ArrayList<Player> players, Area area) {
		
		Scanner scanner  = new Scanner(System.in);
		
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
			area.setOwner(highestBidder);
			
			System.out.println(highestBidder.getName() + "wins the auction for " + highestBid);
		} else {
			System.out.println("No one bid. Area remains unowned");
		}
	}
	
	
	
	
	public static void askToBuyMajorDevelopment(Player p, Board board, Area area, Development development) {
		
		Scanner scanner = new Scanner(System.in);
		
		if (p.getCurrency() >= development.getCost()) {
			
			System.out.println(p.getName() + ", would you like to carry out a major development? If yes, press y. If no, press n: ");
			
			String choice = scanner.next();			
			if (choice.equalsIgnoreCase("y")) {
				int newCurrency = p.getCurrency() - development.getCost();
				p.setCurrency(newCurrency);
				
				int developments = area.getMajorDevOwnedCounter();
				developments++;
				area.setMajorDevOwnedCounter(developments);
				
			}else if (choice.equalsIgnoreCase("n")) {
				System.out.println("Maybe next time.");
				
			}else {
				System.out.println("Invalid input. Please enter y or n: ");
			}
			
		}else {
			System.out.println("Sorry! You don't have enough volunteer hours to carry out a major development right now. Try again later!");
		}
		
	
	}

}
