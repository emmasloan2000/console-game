package save.our.planet;

import java.util.Random;

public class Chance {

	private static final Random random = new Random();

	// Random effects: 6 positive, 6 negative
	private static final int[] chanceCurrencyChanges = { 50, 100, 150, 60, 120, 180, -50, -100, -150, -60, -120, -180 };

	// Roll with 1 chance rolled
	public static void randomEffect1(Player currentPlayer) {

		int chanceIndex = random.nextInt(chanceCurrencyChanges.length);
		int effect = chanceCurrencyChanges[chanceIndex];

		currentPlayer.setCurrency(currentPlayer.getCurrency() + effect);

		if (effect > 0) {
			System.out.println(currentPlayer.getName() + " has gained " + effect + " Volunteer Hours.");
		} else {
			System.out.println(currentPlayer.getName() + " has lost " + Math.abs(effect) + " Volunteer Hours.");
		}

	}

	// Roll with 2 chances rolled
	public static void randomEffect2(Player currentPlayer) {

		System.out.println(currentPlayer.getName() + " rolled a chance on both dice.");
		randomEffect1(currentPlayer);
		randomEffect1(currentPlayer);

	}

	// Move player
	public static void movePlayer(Player currentPlayer, int steps, Board board) {
		int newPos = Board.findNewPosition(currentPlayer.getPosition(), steps);
		currentPlayer.setPosition(newPos);
		
		Square currentSquare = board.findPlayerSquare(newPos);

		System.out.println(currentPlayer.getName() + " moves " + steps + " spaces to " + currentSquare.getName());

	}
}
