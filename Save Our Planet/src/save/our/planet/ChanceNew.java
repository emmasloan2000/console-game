package save.our.planet;

import java.util.Random;

public class ChanceNew {

	private Random random = new Random();

	public void randomEffect1(Player player) {

		int effect = random.nextInt(6);

		switch (effect) {

		case 0:
			System.out.println("Advance to Activation Square! Collect 500 volunteer hours.");
			movePlayer(player, 0);
			adjustCurrency(player, 500);
			break;

		case 1:
			System.out.println("New volunteers have joined your team! Collect 400 volunteer hours.");
			adjustCurrency(player, 400);
			break;

		case 2:
			System.out.println("A fundraising event has been organised! Collect 300 volunteer hours.");
			adjustCurrency(player, 300);
			break;

		case 3:
			System.out.println("Renewable energy investment return! Collect 400 volunteer hours.");
			adjustCurrency(player, 400);
			break;

		case 4:
			System.out.println("Your charity anniversary! Receive 300 volunteer hours.");
			adjustCurrency(player, 300);
			break;

		case 5:
			System.out.println("Free pass to buy an area from another player!");
			break;
		}
	}

	public void randomEffect2(Player player) {
		int effect = random.nextInt(5);

		switch (effect) {

		case 0:
			System.out.println("Oil spill in Pacific Ocean! Donate 200 volunteer hours.");
			adjustCurrency(player, -200);
			break;

		case 1:
			System.out.println("Amazon rainforest fire! Donate 200 volunteer hours.");
			adjustCurrency(player, -200);
			break;

		case 2:
			System.out.println("Storm damage at the North Pole! Donate 200 volunteer hours.");
			adjustCurrency(player, -200);
			break;

		case 3:
			System.out.println("Pollution in Seoul! Donate 200 volunteer hours.");
			adjustCurrency(player, -200);
			break;

		case 4:
		    System.out.println("Upkeep for developments! Donate 50 per development.");
		    int cost = player.getDevelopments().size() * 50;
		    adjustCurrency(player, -cost);
		    break;
		}
	}

	public void movePlayer(Player player, int spaces) {
		player.setPosition(player.getPosition() + spaces);
	}

	public void adjustCurrency(Player player, int amount) {
		player.setCurrency(player.getCurrency() + amount);
	}

}
